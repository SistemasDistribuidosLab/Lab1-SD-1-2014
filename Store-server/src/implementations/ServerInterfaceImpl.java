package implementations;

import entities.*;
import interfaces.*;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpacontrollers.*;
import jpacontrollers.exceptions.IllegalOrphanException;
import jpacontrollers.exceptions.NonexistentEntityException;

/**
 *
 * @author sylar
 */
public class ServerInterfaceImpl extends UnicastRemoteObject implements ServerInterface {

    private ArrayList clients = new ArrayList();
    private ArrayList clientes = new ArrayList();
    private static ArrayList ips = new ArrayList();

    /*  Persistencia    */
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Store-serverPU");
    EntityManager em = emf.createEntityManager();
    /*  Controladores   */
    RoleJpaController roleJpaController = new RoleJpaController(emf); //create an instance of your jpa controller and pass in the injected emf 
    UserJpaController userJpaController = new UserJpaController(emf); //create an instance of your jpa controller and pass in the injected emf 
    AddressJpaController addressJpaController = new AddressJpaController(emf);
    CatalogJpaController catalogJpaController = new CatalogJpaController(emf);
    CategoryJpaController categoryJpaController = new CategoryJpaController(emf);
    CityJpaController cityJpaController = new CityJpaController(emf);
    ClientJpaController clientJpaController = new ClientJpaController(emf);
    CompanyJpaController companyJpaController = new CompanyJpaController(emf);
    CountryJpaController countryJpaController = new CountryJpaController(emf);
    ItemJpaController itemJpaController = new ItemJpaController(emf);
    ProductJpaController productJpaController = new ProductJpaController(emf);
    LogJpaController logJpaController = new LogJpaController(emf);

    public void enviarMensaje(String emisor, String receptor, String mensaje) throws RemoteException {
        System.out.println(emisor + ": "+ mensaje);
        ClientInterface Receptor = (ClientInterface) clients.get(clientes.indexOf(receptor));
        Receptor.recibirMensaje(emisor, receptor, mensaje);
        GenerarLog("EnvioMensaje", getUserByEmail(emisor));
    }

    public void AbrirChat(String emisor, String receptor) throws RemoteException {
        ClientInterface Receptor = (ClientInterface) clients.get(clientes.indexOf(receptor));
        //Receptor.AbrirChat(emisor);
        Receptor.AbreElChat(emisor);
        GenerarLog("AbrioChat", getUserByEmail(emisor));
    }

    public void meDesconecte(String usuario) throws RemoteException {
        System.out.println("Desconectando...");
        int index = clientes.indexOf(usuario);
        ClientInterface client = (ClientInterface) clients.get(index);
        clientUnregistry(client, usuario);
        GenerarLog("SeDesconecto", getUserByIp());
    }

    User getUserByEmail(String usuario){
        int index = clientes.indexOf(usuario);        
        List<User> userList = userJpaController.findUserEntities();
        User user = null;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserEmail().contentEquals(usuario)) {
                user = userList.get(i);
                return user;
            }
        }
        return null;
    }
    
    public void GenerarLog(String evento, User user) {
        System.out.println("eeeeeeeeeeeee");
        if (user != null) {
            Log log = new Log();
            log.setUserId(user);
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            
            log.setDateLog(new Date());
            log.setActionLog(evento);
            
            String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
            log.setTimeLog(new Date());
            logJpaController.create(log);
            System.out.println(evento + " | " + user.getUserEmail() + " | " + date + " | " + hora);
        }
        System.out.println("ttttttttttttttttttt");
    }

    public ServerInterfaceImpl() throws RemoteException {
        super();
    }

    //Autentificación por implementar de manera descente xD

    public boolean sessionBegin(String email, String pass) throws RemoteException {
        List<User> userList = userJpaController.findUserEntities();
        if (!userList.isEmpty()) {
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUserEmail().contentEquals(email)) {
                    if (userList.get(i).getUserPassword().contentEquals(pass)) {
                        GenerarLog("InicioSesion", getUserByIp());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Este método registra clientes que se conectan

    public synchronized void clientRegistry(ClientInterface client, String name) throws RemoteException {
        if (!(clients.contains(client))) {
            clientes.add(name);
            System.out.println("Buscando ip...");
            try {
                
                System.out.println("La ip es: " + RemoteServer.getClientHost());
                ips.add(RemoteServer.getClientHost());
                System.out.println("Largo : " + ips.size());
            } catch (ServerNotActiveException ex) {
                Logger.getLogger(ServerInterfaceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Cague u.u");
            clients.add(client);
            //clientesNombre.addElement(Nombre);
            for (int i = 0; i < clients.size(); i++) {
                ClientInterface nextClient = (ClientInterface) clients.get(i);
                if (!client.toString().equals(nextClient.toString())) {
                    //Mando la notificacion de que se conecto otro usuario
                    nextClient.notify(name);
                } else {
                    // si es el mismo, le notifico del nombre de todos los demas
                    for (int j = 0; j < clientes.size(); j++) {
                        //System.out.println("Comparando " + clientes.get(j) + " != " + name);
                        if (!clientes.get(j).equals(name)) {
                            //System.out.println("\tEnviando...");
                            client.notify((String) clientes.get(j));
                        }
                    }
                }
            }
        }
    }

    //Este método elimina clientes y notifica la desconexion de alguno.
    public synchronized void clientUnregistry(ClientInterface client, String name) throws RemoteException {
        System.out.println("Eliminare...");
        if (clients.remove(client) && clientes.remove(name)) {
            try {
                ips.remove(RemoteServer.getClientHost());
            } catch (ServerNotActiveException ex) {
                Logger.getLogger(ServerInterfaceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            //clientesNombre.removeElement(Nombre);
            System.out.println("Notificare...");
            for (int i = 0; i < clients.size(); i++) {
                System.out.println("Notificando a: " + i);
                ClientInterface nextClient = (ClientInterface) clients.get(i);
                //Mando la notificacion de que se conecto otro usuario
                nextClient.seDesconecto(name);
            }
        } else {
            System.out.print("Cliente no estaba registrado");
        }
    }

    /*  Métodos correspondientes al CRUD de ROLE*/
    public void createRole(Role role) throws RemoteException {
        roleJpaController.create(role); //persist the entity                   
        GenerarLog("CreateRole", getUserByIp());
        
        
    }

    public void editRole(Role role) throws RemoteException, Exception {
roleJpaController.edit(role);
GenerarLog("EditRole", getUserByIp());
}

public void destroyRole(Role role) throws RemoteException, Exception {
roleJpaController.destroy(role.getRoleId());
GenerarLog("DestroyRole", getUserByIp());
}

public List<Role> getRoleList() throws RemoteException {
GenerarLog("Role", getUserByIp());
return roleJpaController.findRoleEntities();
}

public Role findRole(Integer idRole) throws RemoteException {
    GenerarLog("FindRole", getUserByIp());
return roleJpaController.findRole(idRole);
}

    /*  Métodos correspondientes al CRUD de USER*/
    public void createUser(User user, Role role) throws RemoteException {
        user.setRoleId(role);
        GenerarLog("CreateUser", getUserByIp());
        userJpaController.create(user); //persist the entity                   
    }

    public void createUser(User user, Company company, Role role) throws RemoteException {
        user.setCompanyId(company);
        user.setRoleId(role);
        GenerarLog("CreateUser", getUserByIp());
        userJpaController.create(user); //persist the entity                   
    }

    public void editUser(User user) throws RemoteException, Exception {
        userJpaController.edit(user);
        GenerarLog("EditUser", getUserByIp());
    }

    public void destroyUser(User user) throws RemoteException, Exception {
        userJpaController.destroy(user.getUserId());
        GenerarLog("DestroyUser", getUserByIp());
    }

    public List<User> getUserList() throws RemoteException {
        GenerarLog("getUserList", getUserByIp());
        return userJpaController.findUserEntities();
    }

    public User findUser(Integer idUser) throws RemoteException {
        GenerarLog("FindUser", getUserByIp());
        return userJpaController.findUser(idUser);
    }

    /*Metodos correspondientes al CRUD de Address */
    public void createAddress(Address address) throws RemoteException {
        GenerarLog("CreateAddress", getUserByIp());
        addressJpaController.create(address);
    }

    public void editAddress(Address address) throws RemoteException, Exception {
        GenerarLog("EditAddress", getUserByIp());
        addressJpaController.edit(address);
    }

    public void destroyAddress(Address address) throws RemoteException, Exception {
        GenerarLog("DestroyAddress", getUserByIp());
        addressJpaController.destroy(address.getAddressId());
    }

    public List<Address> getAddressList() throws RemoteException {
        GenerarLog("GetAddressList", getUserByIp());
        return addressJpaController.findAddressEntities();
    }

    public Address findAddress(Integer idAddress) throws RemoteException {
        GenerarLog("FindAddress", getUserByIp());
        return addressJpaController.findAddress(idAddress);
    }

    /*Metodos correspondientes al CRUD de Catalog */
    public void createCatalog(Catalog catalog) throws RemoteException {
        GenerarLog("CreateCatalog", getUserByIp());
        catalogJpaController.create(catalog);
    }

    public void editCatalog(Catalog catalog) throws RemoteException, Exception {
        GenerarLog("EditCatalog", getUserByIp());
        catalogJpaController.edit(catalog);
    }

    public void destroyCatalog(Catalog catalog) throws RemoteException, Exception {
        GenerarLog("DestroyCatalog", getUserByIp());
        catalogJpaController.destroy(catalog.getCatalogId());
    }

    public List<Catalog> getCatalogList() throws RemoteException {
        GenerarLog("GetCatalogList", getUserByIp());
        return catalogJpaController.findCatalogEntities();
    }

    public Catalog findCatalog(Integer idCatalog) throws RemoteException {
        GenerarLog("FindCatalog", getUserByIp());
        return catalogJpaController.findCatalog(idCatalog);
    }

    /*Metodos correspondientes al CRUD de Category */
    public void createCategory(Category category) throws RemoteException {
        GenerarLog("CreateCategory", getUserByIp());
        categoryJpaController.create(category);
    }

    public void editCategory(Category category) throws RemoteException, Exception {
        GenerarLog("EditCategory", getUserByIp());
        categoryJpaController.edit(category);
    }

    public void destroyCategory(Category category) throws RemoteException, Exception {
        GenerarLog("DestroyCategory", getUserByIp());
        categoryJpaController.destroy(category.getCategoryId());
    }

    public List<Category> getCategoryList() throws RemoteException {
    GenerarLog("GetCategoryList", getUserByIp());
    return categoryJpaController.findCategoryEntities();
}

public Category findCategory(Integer idCategory) throws RemoteException {
    GenerarLog("FindCategory", getUserByIp());
    return categoryJpaController.findCategory(idCategory);
}

/*Metodos correspondientes al CRUD de City */
public void createCity(City city) throws RemoteException {
    GenerarLog("CreateCity", getUserByIp());
    cityJpaController.create(city);
}

public void editCity(City city) throws RemoteException, Exception {
    GenerarLog("EditCity", getUserByIp());
    cityJpaController.edit(city);
}

public void destroyCity(City city) throws RemoteException, Exception {
    GenerarLog("DestroyCity", getUserByIp());
    cityJpaController.destroy(city.getCityId());
}

public List<City> getCityList() throws RemoteException {
    GenerarLog("GetCityList", getUserByIp());
    return cityJpaController.findCityEntities();
}

public City findCity(Integer idCity) throws RemoteException {
    GenerarLog("FindCity", getUserByIp());
    return cityJpaController.findCity(idCity);
}

/*Metodos correspondientes al CRUD de Client */
public void createClient(Client client) throws RemoteException {
    GenerarLog("CreateClient", getUserByIp());
    clientJpaController.create(client);
}

public void editClient(Client client) throws RemoteException, Exception {
    GenerarLog("EditClient", getUserByIp());
    clientJpaController.edit(client);
}

public void destroyClient(Client client) throws RemoteException, Exception {
    GenerarLog("DestroyClient", getUserByIp());
    clientJpaController.destroy(client.getClientId());
}

public List<Client> getClientList() throws RemoteException {
    GenerarLog("GetClientList", getUserByIp());
    return clientJpaController.findClientEntities();
}

public Client findClient(Integer idClient) throws RemoteException {
    GenerarLog("FindClient", getUserByIp());
    return clientJpaController.findClient(idClient);
}

/*Metodos correspondientes al CRUD de Company */
public void createCompany(Company company) throws RemoteException {
    GenerarLog("CreateCompany", getUserByIp());
    companyJpaController.create(company);
}

public void editCompany(Company company) throws RemoteException, Exception {
    GenerarLog("EditCompany", getUserByIp());
    companyJpaController.edit(company);
}

public void destroyCompany(Company company) throws RemoteException, Exception {
    GenerarLog("DestroyCompany", getUserByIp());
    companyJpaController.destroy(company.getCompanyId());
}

public List<Company> getCompanyList() throws RemoteException {
    GenerarLog("GetCompanyList", getUserByIp());
    return companyJpaController.findCompanyEntities();
}

public Company findCompany(Integer idCompany) throws RemoteException {
    GenerarLog("FindCompany", getUserByIp());
    return companyJpaController.findCompany(idCompany);
}

/*Metodos correspondientes al CRUD de Country */
public void createCountry(Country country) throws RemoteException {
    GenerarLog("CreateCountry", getUserByIp());
    countryJpaController.create(country);
}

public void editCountry(Country country) throws RemoteException, Exception {
    GenerarLog("EditCountry", getUserByIp());
    countryJpaController.edit(country);
}

public void destroyCountry(Country country) throws RemoteException, Exception {
    GenerarLog("DestroyCountry", getUserByIp());
    countryJpaController.destroy(country.getCountryId());
}

public List<Country> getCountryList() throws RemoteException {
    GenerarLog("GetCountryList", getUserByIp());
    return countryJpaController.findCountryEntities();
}

public Country findCountry(Integer idCountry) throws RemoteException {
    GenerarLog("FindCountry", getUserByIp());
    return countryJpaController.findCountry(idCountry);
}

/*Metodos correspondientes al CRUD de Item */
public void createItem(Item item) throws RemoteException {
    GenerarLog("CreateItem", getUserByIp());
    itemJpaController.create(item);
}

public void editItem(Item item) throws RemoteException, Exception {
    GenerarLog("EditItem", getUserByIp());
    itemJpaController.edit(item);
}

public void destroyItem(Item item) throws RemoteException, Exception {
    GenerarLog("DestroyItem", getUserByIp());
    itemJpaController.destroy(item.getItemId());
}

public List<Item> getItemList() throws RemoteException {
    GenerarLog("GetItemList", getUserByIp());
    return itemJpaController.findItemEntities();
}

public Item findItem(Integer idItem) throws RemoteException {
    GenerarLog("FindItem", getUserByIp());
    return itemJpaController.findItem(idItem);
}

/*Metodos correspondientes al CRUD de Product */
public void createProduct(Product product) throws RemoteException {
    GenerarLog("CreateProduct", getUserByIp());
    productJpaController.create(product);
}

public void editProduct(Product product) throws RemoteException, Exception {
    GenerarLog("EditProduct", getUserByIp());
    productJpaController.edit(product);
}

public void destroyProduct(Product product) throws RemoteException, Exception {
    GenerarLog("DestroyProduct", getUserByIp());
    productJpaController.destroy(product.getProductId());
}

public List<Product> getProductList() throws RemoteException {
    GenerarLog("GetProductList", getUserByIp());
    return productJpaController.findProductEntities();
}

public Product findProduct(Integer idProduct) throws RemoteException {
    GenerarLog("FindProduct", getUserByIp());
    return productJpaController.findProduct(idProduct);
}

/*Metodos correspondientes al CRUD de Product */
public void createLog(Log log) throws RemoteException {
    GenerarLog("CreateLog", getUserByIp());
    logJpaController.create(log);
}

public void editLog(Log log) throws RemoteException, Exception {
    GenerarLog("EditLog", getUserByIp());
    logJpaController.edit(log);
}

public void destroyLog(Log log) throws RemoteException, Exception {
    GenerarLog("DestroyLog", getUserByIp());
    logJpaController.destroy(log.getIdLog());
}

public List<Log> getLogList() throws RemoteException {
    GenerarLog("GetLogList", getUserByIp());
    return logJpaController.findLogEntities();
}

public Log findLog(Integer idLog) throws RemoteException {
    GenerarLog("FindLog", getUserByIp());
    return logJpaController.findLog(idLog);
}

    private User getUserByIp(){
        try{
            String ip = RemoteServer.getClientHost();
            for (int i = 0; i < ips.size(); i++) {
                System.out.println(ip + " == " + ips.get(i));
                if(ip.equals(ips.get(i))){
                    System.out.println("uuuuuuuuu");
                    return (User) getUserByEmail((String) clientes.get(i));
                }
            }
        }catch(ServerNotActiveException e){
            e.printStackTrace();
        }
        System.out.println("kkkkkkkkkkk");
        return null;
    }
}
