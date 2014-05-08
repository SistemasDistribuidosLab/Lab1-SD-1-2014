/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import entities.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author sylar
 */
public interface ServerInterface extends Remote{
    public boolean sessionBegin(String name, String pass) throws RemoteException;
    public void clientRegistry(ClientInterface client, String name) throws RemoteException;
    public void clientUnregistry(ClientInterface client, String name) throws RemoteException;
    public void enviarMensaje(String emisor, String receptor, String mensaje) throws RemoteException;
    public void AbrirChat(String emisor, String receptor) throws RemoteException;
    public void meDesconecte(String usuario) throws RemoteException;
    
    /*  Métodos correspondientes al CRUD de ROLE*/
    public void createRole(Role role) throws RemoteException;
    public void editRole(Role role) throws RemoteException, Exception;
    public void destroyRole(Role role) throws RemoteException, Exception;
    public List<Role> getRoleList() throws RemoteException;
    public Role findRole(Integer idRole) throws RemoteException;
    
    /*  Métodos correspondientes al CRUD de USER*/
    public void createUser(User user, Role role) throws RemoteException;
    public void createUser(User user, Company company, Role role) throws RemoteException;    
    public void editUser(User user) throws RemoteException, Exception;
    public void destroyUser(User user) throws RemoteException, Exception;
    public List<User> getUserList() throws RemoteException;
    public User findUser(Integer idUser) throws RemoteException;
    public User getUserByEmail(String email) throws RemoteException; 
    
     /*  Métodos correspondientes al CRUD de Address*/
    public void createAddress(Address address) throws RemoteException;    
    public void editAddress(Address address) throws RemoteException, Exception;
    public void destroyAddress(Address address)throws RemoteException, Exception;
    public List<Address> getAddressList() throws RemoteException;
    public Address findAddress(Integer idAddress) throws RemoteException;
    
      /*  Métodos correspondientes al CRUD de Catalog*/
    public void createCatalog(Catalog catalog) throws RemoteException;    
    public void editCatalog(Catalog catalog) throws RemoteException, Exception;
    public void destroyCatalog(Catalog catalog)throws RemoteException, Exception;
    public List<Catalog> getCatalogList() throws RemoteException;
    public Catalog findCatalog(Integer idCatalog) throws RemoteException;
    
      /*  Métodos correspondientes al CRUD de Category*/
    public void createCategory(Category category) throws RemoteException;    
    public void editCategory(Category category) throws RemoteException, Exception;
    public void destroyCategory(Category category)throws RemoteException, Exception;
    public List<Category> getCategoryList() throws RemoteException;
    public Category findCategory(Integer idCategory) throws RemoteException;
    
      /*  Métodos correspondientes al CRUD de City*/
    public void createCity(City city) throws RemoteException;    
    public void editCity(City city) throws RemoteException, Exception;
    public void destroyCity(City city)throws RemoteException, Exception;
    public List<City> getCityList() throws RemoteException;
    public City findCity(Integer idCity) throws RemoteException;
    
       /*  Métodos correspondientes al CRUD de Client*/
    public void createClient(Client client) throws RemoteException;    
    public void editClient(Client client) throws RemoteException, Exception;
    public void destroyClient(Client client)throws RemoteException, Exception;
    public List<Client> getClientList() throws RemoteException;
    public Client findClient(Integer idClient) throws RemoteException;
    
       /*  Métodos correspondientes al CRUD de Company*/
    public void createCompany(Company company) throws RemoteException;    
    public void editCompany(Company company) throws RemoteException, Exception;
    public void destroyCompany(Company company)throws RemoteException, Exception;
    public List<Company> getCompanyList() throws RemoteException;
    public Company findCompany(Integer idCompany) throws RemoteException;
    
       /*  Métodos correspondientes al CRUD de Country*/
    public void createCountry(Country country) throws RemoteException;    
    public void editCountry(Country country) throws RemoteException, Exception;
    public void destroyCountry(Country country)throws RemoteException, Exception;
    public List<Country> getCountryList() throws RemoteException;
    public Country findCountry(Integer idCountry) throws RemoteException;
    
         /*  Métodos correspondientes al CRUD de Item*/
    public void createItem(Item item) throws RemoteException;    
    public void editItem(Item item) throws RemoteException, Exception;
    public void destroyItem(Item item)throws RemoteException, Exception;
    public List<Item> getItemList() throws RemoteException;
    public Item findItem(Integer idItem) throws RemoteException;
    
         /*  Métodos correspondientes al CRUD de Product*/
    public void createProduct(Product product) throws RemoteException;    
    public void editProduct(Product product) throws RemoteException, Exception;
    public void destroyProduct(Product product)throws RemoteException, Exception;
    public List<Product> getProductList() throws RemoteException;
    public Product findProduct(Integer idProduct) throws RemoteException;
    
        /*  Métodos correspondientes al CRUD de Log*/
    public void createLog(Log log) throws RemoteException;
    public void editLog(Log log)  throws RemoteException, Exception;
    public void destroyLog(Log log) throws RemoteException, Exception;
    public List<Log> getLogList()throws RemoteException;
    public List<Log> getLogList(User user) throws RemoteException;
    public Log findLog(Integer idLog) throws RemoteException;
    
}
