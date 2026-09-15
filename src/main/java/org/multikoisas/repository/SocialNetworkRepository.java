package org.multikoisas.repository;

import org.multikoisas.entities.SocialNetworkEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SocialNetworkRepository
{

    @Autowired
    private DataSource dataSource;

    public List<SocialNetworkEntity> findAll()
    {
        List<SocialNetworkEntity> socialNetworks = new ArrayList<>();

        try
        {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT id, descricao, link FROM \"redesSociais\"");
            ResultSet rs = ps.executeQuery();

            while( rs.next() )
            {
                SocialNetworkEntity socialNetwork= new SocialNetworkEntity();
                socialNetwork.setId( rs.getInt("id") );
                socialNetwork.setDescricao( rs.getString("descricao") );
                socialNetwork.setLink( rs.getString("link") );

                socialNetworks.add(socialNetwork);
            }

        }
        catch (SQLException e)
        {
            throw new RuntimeException( e );
        }
        return  socialNetworks;
    }
}
