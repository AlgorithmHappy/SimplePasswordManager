package com.mycompany.passwordmanager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mycompany.passwordmanager.dto.AccountDto;
import com.mycompany.passwordmanager.entities.Accounts;
import com.mycompany.passwordmanager.utils.GeneralMethods;
import com.mycompany.passwordmanager.utils.constants.Constants;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/*
 * Clase que realiza las operaciones CRUD en la base de datos
 */
public class CrudServiceImplements implements CrudServiceInterface{

    // Clase que contiene metodos generales, entre ellas la conexion a la base de datos
    private GeneralMethods generalMethods;

    // Repositorio de la base de datos de SQLite
    private EntityManager entityManager;

    /*
     * Constructor que inicializa la clase GeneralMethods, si o si se le tiene que pasar el password de la base de datos
     * @param password Contraseña para encriptar y desencriptar las bases de datos sqlite
     */
    public CrudServiceImplements(String password) {
        generalMethods = GeneralMethods.getInstance(password);
        entityManager = generalMethods.getConexion().getEntityManager();
    }

    /*
     * Metodo que inserta una cuenta en la base de datos
     * @param account Cuenta a insertar
     */
    @Override
    public void insertAccount(AccountDto account) {
        entityManager.getTransaction().begin();
        entityManager.persist(account.getEntityAcount());
        entityManager.getTransaction().commit();
    }

    /*
     * Metodo que actualiza una cuenta en la base de datos
     * @param account Cuenta a actualizar
     */
    @Override
    public void updateAccount(AccountDto account) {
        entityManager.getTransaction().begin();
        entityManager.merge(account.getEntityAcount());
        entityManager.getTransaction().commit();
    }

    /*
     * Metodo que elimina una cuenta en la base de datos
     * @param account Cuenta a eliminar
     */
    @Override
    public void deletetAccount(AccountDto account) {
        entityManager.getTransaction().begin();
        entityManager.remove(account.getEntityAcount());
        entityManager.getTransaction().commit();
    }

    /*
     * Metodo que devuelve todas las cuentas de la base de datos
     * @return Lista de todas las cuentas
     */
    @Override
    public List<AccountDto> getAllAccounts() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(Constants.DATA_BASE_QUERY_ALL_ACCOUNTS);
        List<AccountDto> lstAccountDtos = new ArrayList<>();
        List<Accounts> lstAccounts = query.getResultList();
        if(lstAccounts != null && !lstAccounts.isEmpty() ){
            lstAccountDtos = lstAccounts
                .stream()
                .map(it -> new AccountDto(it) )
                .collect(Collectors.toList() );
        }
        entityManager.getTransaction().commit();
        return lstAccountDtos;
    }

}
