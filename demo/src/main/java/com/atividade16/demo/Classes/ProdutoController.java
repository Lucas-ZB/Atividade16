package com.atividade16.demo.Classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para gerenciar operações relacionadas a produtos.
 * Este controlador expõe endpoints para criar, listar, buscar, atualizar e deletar produtos.
 */
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    /**
     * Cria um novo produto.
     *
     * @param produto O objeto Produto a ser criado.
     * @return O produto criado.
     */
    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto) {
        return service.criarProduto(produto);
    }

    /**
     * Lista todos os produtos disponíveis.
     *
     * @return Uma lista de produtos.
     */
    @GetMapping
    public List<Produto> listarProdutos() {
        return service.listarProdutos();
    }

    /**
     * Busca um produto específico pelo seu ID.
     *
     * @param id O ID do produto a ser buscado.
     * @return Um ResponseEntity contendo o produto encontrado ou um status 404 se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        return service.buscarProdutoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza as informações de um produto existente.
     *
     * @param id O ID do produto a ser atualizado.
     * @param produtoAtualizado O objeto Produto com as novas informações.
     * @return Um ResponseEntity contendo o produto atualizado ou um status 404 se o produto não for encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        try {
            Produto produto = service.atualizarProduto(id, produtoAtualizado);
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deleta um produto pelo seu ID.
     *
     * @param id O ID do produto a ser deletado.
     * @return Um ResponseEntity com status 204 (No Content) se a operação for bem-sucedida.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        service.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}