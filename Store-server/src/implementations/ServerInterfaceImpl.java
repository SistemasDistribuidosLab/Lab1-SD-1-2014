
package implementations;

import entities.Address;
import entities.Catalog;
import entities.Category;
import entities.City;
import entities.Client;
import entities.Company;
import entities.Country;
import entities.Item;
import entities.Product;
import entities.Role;
import entities.User;
import interfaces.*;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.List;
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
public class ServerInterfaceImpl extends UnicastRemoteObject implements ServerInterface{
    private ArrayList clients = new ArrayList();
    private ArrayList clientes = new ArrayList();
    
    
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
    
public void enviarMensaje(String emisor, String receptor, String mensaje) throws RemoteException{
        System.out.println(emisor + ": "+ mensaje);
        ClientInterface Receptor = (ClientInterface) clients.get(clientes.indexOf(receptor));
        Receptor.recibirMensaje(emisor, receptor, mensaje);
    }
    public void AbrirChat(String emisor, String receptor) throws RemoteException{
        ClientInterface Receptor = (ClientInterface) clients.get(clientes.indexOf(receptor));
        //Receptor.AbrirChat(emisor);
        Receptor.AbreElChat(emisor);
    }
    
    public void meDesconecte(String usuario) throws RemoteException{
        System.out.println("Desconectando...");
        int index = clientes.indexOf(usuario);
        ClientInterface client = (ClientInterface) clients.get(index);
        clientUnregistry(client, usuario);
    }

    public ServerInterfaceImpl() throws RemoteException{
        super();
    }
    //Autentificación por implementar de manera descente xD
    public boolean sessionBegin(String name, String pass) throws RemoteException {
        if (name.equals("juan") && pass.equals("juan")){
            return true;
        }
        else if(name.equals("pedro") && pass.equals("pedro")){
            return true;
        }
        else if (name.equals("jose") && pass.equals("jose")){
            return true;
        }
        return false;
    }
    //Este método registra clientes que se conectan
    public synchronized void clientRegistry(ClientInterface client, String name) throws RemoteException{
        if (!(clients.contains(client))) {
            clientes.add(name);
            clients.add(client);
            //clientesNombre.addElement(Nombre);
            for (int i=0;i<clients.size();i++){
                ClientInterface nextClient = (ClientInterface)clients.get(i);
                if (!client.toString().equals(nextClient.toString())){
                    //Mando la notificacion de que se conecto otro usuario
                    nextClient.notify(name);
                }else{
                    // si es el mismo, le notifico del nombre de todos los demas
                    for(int j = 0; j < clientes.size(); j++){
                        //System.out.println("Comparando " + clientes.get(j) + " != " + name);
                        if(!clientes.get(j).equals(name)){
                            //System.out.println("\tEnviando...");
                            client.notify((String) clientes.get(j));
                        }
                    }
                }
            }
        }
    }
    
    //Este método elimina clientes y notifica la desconexion de alguno.
    public synchronized void clientUnregistry(ClientInterface client, String name) throws RemoteException{
        System.out.println("Eliminare...");
        if (clients.remove(client) && clientes.remove(name)) {
            //clientesNombre.removeElement(Nombre);
            System.out.println("Notificare...");
            for (int i=0;i<clients.size();i++){
                System.out.println("Notificando a: "+i);
                ClientInterface nextClient = (ClientInterface)clients.get(i);
                //Mando la notificacion de que se conecto otro usuario
                nextClient.seDesconecto(name);
            }
        }
        else{
            System.out.print("Cliente no estaba registrado");
        }
    }
    
    /*  Métodos correspondientes al CRUD de ROLE*/
    public void createRole(Role role) throws RemoteException {
        roleJpaController.create(role); //persist the entity                   
    }
    
    public void editRole(Role role) throws RemoteException, Exception {
        roleJpaController.edit(role);
    }
    
    public void destroyRole(Role role) throws RemoteException, Exception {
        roleJpaController.destroy(role.getRoleId());
    }
    
    public List<Role> getRoleList() throws RemoteException {
        return roleJpaController.findRoleEntities();
    } 
    
    public Role findRole(Integer idRole) throws RemoteException {
        return roleJpaController.findRole(idRole);
    }
    
    /*  Métodos correspondientes al CRUD de USER*/
    public void createUser(User user, Role role) throws RemoteException {
        user.setRoleId(role);
        userJpaController.create(user); //persist the entity                   
    }
    
    public void createUser(User user, Company company, Role role) throws RemoteException {
        user.setCompanyId(company);
        user.setRoleId(role);
        userJpaController.create(user); //persist the entity                   
    }
    
    public void editUser(User user) throws RemoteException, Exception {
        userJpaController.edit(user);
    }
    
    public void destroyUser(User user) throws RemoteException, Exception {
        userJpaController.destroy(user.getUserId());
    }
    
    public List<User> getUserList() throws RemoteException {
        return userJpaController.findUserEntities();
    } 

    public User findUser(Integer idUser) throws RemoteException {
        return userJpaController.findUser(idUser);
    }
    
    /*Metodos correspondientes al CRUD de Address */    
    public void createAddress(Address address) throws RemoteException{
        addressJpaController.create(address);
    }
    
    public void editAddress(Address address)  throws RemoteException, Exception {
        addressJpaController.edit(address);
    }
    
    public void destroyAddress(Address address) throws RemoteException, Exception{
        addressJpaController.destroy(address.getAddressId());
    }
    
    public List<Address> getAddressList()throws RemoteException{
        return addressJpaController.findAddressEntities();
    }
    
    public Address findAddress(Integer idAddress) throws RemoteException{
        return addressJpaController.findAddress(idAddress);
    }
    
      /*Metodos correspondientes al CRUD de Catalog */    
    public void createCatalog(Catalog catalog) throws RemoteException{
        catalogJpaController.create(catalog);
    }
    
    public void editCatalog(Catalog catalog)  throws RemoteException, Exception {
        catalogJpaController.edit(catalog);
    }
    
    public void destroyCatalog(Catalog catalog) throws RemoteException, Exception{
        catalogJpaController.destroy(catalog.getCatalogId());
    }
    
    public List<Catalog> getCatalogList()throws RemoteException{
        return catalogJpaController.findCatalogEntities();
    }
    
    public Catalog findCatalog(Integer idCatalog) throws RemoteException{
        return catalogJpaController.findCatalog(idCatalog);
    }
    
    /*Metodos correspondientes al CRUD de Category */    
    public void createCategory(Category category) throws RemoteException{
        categoryJpaController.create(category);
    }
    
    public void editCategory(Category category)  throws RemoteException, Exception {
        categoryJpaController.edit(category);
    }
    
    public void destroyCategory(Category category) throws RemoteException, Exception{
        categoryJpaController.destroy(category.getCategoryId());
    }
    
    public List<Category> getCategoryList()throws RemoteException{
        return categoryJpaController.findCategoryEntities();
    }
    
    public Category findCategory(Integer idCategory) throws RemoteException{
        return categoryJpaController.findCategory(idCategory);
    }
    
    /*Metodos correspondientes al CRUD de City */    
    public void createCity(City city) throws RemoteException{
        cityJpaController.create(city);
    }
    
    public void editCity(City city)  throws RemoteException, Exception {
        cityJpaController.edit(city);
    }
    
    public void destroyCity(City city) throws RemoteException, Exception{
        cityJpaController.destroy(city.getCityId());
    }
    
    public List<City> getCityList()throws RemoteException{
        return cityJpaController.findCityEntities();
    }
    
    public City findCity(Integer idCity) throws RemoteException{
        return cityJpaController.findCity(idCity);
    }
    
    /*Metodos correspondientes al CRUD de Client */    
    public void createClient(Client client) throws RemoteException{
        clientJpaController.create(client);
    }
    
    public void editClient(Client client)  throws RemoteException, Exception {
        clientJpaController.edit(client);
    }
    
    public void destroyClient(Client client) throws RemoteException, Exception{
        clientJpaController.destroy(client.getClientId());
    }
    
    public List<Client> getClientList()throws RemoteException{
        return clientJpaController.findClientEntities();
    }
    
    public Client findClient(Integer idClient) throws RemoteException{
        return clientJpaController.findClient(idClient);
    }
    
     /*Metodos correspondientes al CRUD de Company */    
    public void createCompany(Company company) throws RemoteException{
        companyJpaController.create(company);
    }
    
    public void editCompany(Company company)  throws RemoteException, Exception {
        companyJpaController.edit(company);
    }
    
    public void destroyCompany(Company company) throws RemoteException, Exception{
        companyJpaController.destroy(company.getCompanyId());
    }
    
    public List<Company> getCompanyList()throws RemoteException{
        return companyJpaController.findCompanyEntities();
    }
    
    public Company findCompany(Integer idCompany) throws RemoteException{
        return companyJpaController.findCompany(idCompany);
    }
    
      /*Metodos correspondientes al CRUD de Country */    
    public void createCountry(Country country) throws RemoteException{
        countryJpaController.create(country);
    }
    
    public void editCountry(Country country)  throws RemoteException, Exception {
        countryJpaController.edit(country);
    }
    
    public void destroyCountry(Country country) throws RemoteException, Exception{
        countryJpaController.destroy(country.getCountryId());
    }
    
    public List<Country> getCountryList()throws RemoteException{
        return countryJpaController.findCountryEntities();
    }
    
    public Country findCountry(Integer idCountry) throws RemoteException{
        return countryJpaController.findCountry(idCountry);
    }
    
     /*Metodos correspondientes al CRUD de Item */    
    public void createItem(Item item) throws RemoteException{
        itemJpaController.create(item);
    }
    
    public void editItem(Item item)  throws RemoteException, Exception {
        itemJpaController.edit(item);
    }
    
    public void destroyItem(Item item) throws RemoteException, Exception{
        itemJpaController.destroy(item.getItemId());
    }
    
    public List<Item> getItemList()throws RemoteException{
        return itemJpaController.findItemEntities();
    }
    
    public Item findItem(Integer idItem) throws RemoteException{
        return itemJpaController.findItem(idItem);
    }
    
       /*Metodos correspondientes al CRUD de Product */    
    public void createProduct(Product product) throws RemoteException{
        productJpaController.create(product);
    }
    
    public void editProduct(Product product)  throws RemoteException, Exception {
        productJpaController.edit(product);
    }
    
    public void destroyProduct(Product product) throws RemoteException, Exception{
        productJpaController.destroy(product.getProductId());
    }
    
    public List<Product> getProductList()throws RemoteException{
        return productJpaController.findProductEntities();
    }
    
    public Product findProduct(Integer idProduct) throws RemoteException{
        return productJpaController.findProduct(idProduct);
    }
}
