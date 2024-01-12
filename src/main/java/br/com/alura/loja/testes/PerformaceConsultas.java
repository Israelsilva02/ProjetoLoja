package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.*;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PerformaceConsultas {
    public static void main(String[] args) {
        popularBancoDeDados();

        EntityManager em = JPAUtil.getEntityManager();
        PedidoDao pedidoDao = new PedidoDao(em);
        Pedido pedido = pedidoDao.buscarPedidoComCliente(1l);
        em.close();
        System.out.println(pedido.getCliente().getNome());


    }

    private static void popularBancoDeDados() {
        Categoria celulares = new Categoria("CELULARES");
        Categoria videogames = new Categoria("VIDEOGAMES");
        Categoria informatica = new Categoria("INFORMATICA");

        Produto celular = new Produto("IPHONE", "Muito Legal", new BigDecimal("800"), celulares);
        Produto videogame = new Produto("PS5", "Viciante", new BigDecimal("4000"), videogames);
        Produto macbook = new Produto("MacbookPRO", "Computador que vou comprar com certeza", new BigDecimal("80000"), informatica);


        Cliente cliente = new Cliente("Israel", "123456 ");

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, celular));
        pedido.adicionarItem(new ItemPedido(40, pedido, videogame));

        Pedido pedido2 = new Pedido(cliente);
        pedido2.adicionarItem(new ItemPedido(2, pedido, macbook));
        pedido2.adicionarItem(new ItemPedido(10, pedido, celular));

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        produtoDao.cadastrar(celular);
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ClienteDao clienteDao = new ClienteDao(em);
        ProdutoDao pedidoDao = new ProdutoDao(em);

        em.getTransaction().begin();

        categoriaDao.cadastrar(celulares);
        categoriaDao.cadastrar(videogames);
        categoriaDao.cadastrar(informatica);

        produtoDao.cadastrar(celular);
        produtoDao.cadastrar(videogame);
        produtoDao.cadastrar(macbook);

        clienteDao.cadastrar(cliente);


        em.getTransaction().commit();
        em.close();
    }
}
