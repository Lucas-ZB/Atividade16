package com.atividade16.demo;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.atividade16.demo.Classes.Produto;
import com.atividade16.demo.Classes.ProdutoController;
import com.atividade16.demo.Classes.ProdutoService;

/**
 * Classe de teste para o controlador ProdutoController.
 * Esta classe utiliza MockMvc para simular requisições HTTP e testar os endpoints da API.
 */
@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService service;

    /**
     * Testa a criação de um novo produto.
     * Verifica se o produto é criado corretamente e se a resposta contém os dados esperados.
     *
     * @throws Exception Se ocorrer um erro durante a execução do teste.
     */
    @Test
    public void testCriarProduto() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto Teste");

        when(service.criarProduto(any(Produto.class))).thenReturn(produto);

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\": \"Produto Teste\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"));
    }

    /**
     * Testa a listagem de produtos.
     * Verifica se a requisição retorna um status 200 OK e se o método listarProdutos do serviço é chamado.
     *
     * @throws Exception Se ocorrer um erro durante a execução do teste.
     */
    @Test
    public void testListarProdutos() throws Exception {
        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk());

        verify(service, times(1)).listarProdutos();
    }

    /**
     * Testa a busca de um produto pelo ID.
     * Verifica se o produto é retornado corretamente quando encontrado.
     *
     * @throws Exception Se ocorrer um erro durante a execução do teste.
     */
    @Test
    public void testBuscarProdutoPorId() throws Exception {
        Long id = 1L;
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome("Produto Teste");

        when(service.buscarProdutoPorId(id)).thenReturn(Optional.of(produto));

        mockMvc.perform(get("/api/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"));

        verify(service, times(1)).buscarProdutoPorId(id);
    }

    /**
     * Testa a atualização de um produto existente.
     * Verifica se o produto é atualizado corretamente e se a resposta contém os dados esperados.
     *
     * @throws Exception Se ocorrer um erro durante a execução do teste.
     */
    @Test
    public void testAtualizarProduto() throws Exception {
        Long id = 1L;
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome("Produto Teste");

        when(service.atualizarProduto(eq(id), any(Produto.class))).thenReturn(produto);

        mockMvc.perform(put("/api/produtos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\": \"Produto Teste\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"));
    }

    /**
     * Testa a deleção de um produto existente.
     * Verifica se o produto é deletado corretamente e se o método deletarProduto do serviço é chamado.
     *
     * @throws Exception Se ocorrer um erro durante a execução do teste.
     */
    @Test
    public void testDeletarProduto() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/produtos/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).deletarProduto(id);
    }
}