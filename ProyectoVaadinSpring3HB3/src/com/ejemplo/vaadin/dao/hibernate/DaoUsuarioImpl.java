package com.ejemplo.vaadin.dao.hibernate;

/**
 *
 * @author
 */
import com.ejemplo.vaadin.dao.DaoUsuario;
import com.ejemplo.vaadin.entidades.Usuario;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DaoUsuarioImpl extends DaoGenericoImpl<Usuario> implements DaoUsuario, Serializable {

    /**
     * {@inheritDoc}
     */
    public List<Usuario> getActivos() {
        List<Usuario> retorno = null;
        try {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuario.class);
            criteria.add(Restrictions.isNull("fechainactivado"));
            criteria.addOrder(Order.asc("nombres"));
            retorno = (List<Usuario>) criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    /**
     * {@inheritDoc}
     */
    public Usuario getActivoByCorreo(String correo) {
        Usuario retorno = null;
        try {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("correo", correo));
            criteria.add(Restrictions.isNull("fechainactivado"));
            retorno = (Usuario) criteria.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }
}
