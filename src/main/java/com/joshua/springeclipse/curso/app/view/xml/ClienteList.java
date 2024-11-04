package com.joshua.springeclipse.curso.app.view.xml;

import java.util.List;

import com.joshua.springeclipse.curso.app.models.entity.Cliente;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "clienteList")
public class ClienteList {

    
    private List<Cliente> clientes;

    public ClienteList() {}

    
    public ClienteList(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @XmlElement(name = "cliente")  // Define cada elemento en la lista como "cliente"
    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
