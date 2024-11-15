package com.atividade16.demo.Classes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço para gerenciar operações relacionadas a produtos.
 * Esta classe encapsula a lógica de negócios e interage com o repositório de produtos.
 */
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    /**
     * Cria um novo produto no sistema.
     *
     * @param produto O objeto Produto a ser criado.
     * @return O produto criado.
     */
    public Produto criarProduto(Produto produto) {
        return repository.save(produto);
    }

    /**
     * Lista todos os produtos disponíveis no sistema.
     *
     * @return Uma lista de produtos.
     */
    public List<Produto> listarProdutos() {
        return repository.findAll();
    }

    /**
     * Busca um produto específico pelo seu ID.
     *
     * @param id O ID do produto a ser buscado.
     * @return Um Optional contendo o produto encontrado, ou vazio se não encontrado.
     */
    public Optional<Produto> buscarProdutoPorId(Long id) {
        return repository.findById(id);
    }

    /**
     * Atualiza as informações de um produto existente.
     *
     * @param id O ID do produto a ser atualizado.
     * @param produtoAtualizado O objeto Produto com as novas informações.
     * @return O produto atualizado.
     * @throws RuntimeException Se o produto não for encontrado no sistema.
     */
    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        return repository.findById(id).map(produto -> {
            produto.setNome(produtoAtualizado.getNome());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
            return repository.save(produto);
        }).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    /**
     * Deleta um produto pelo seu ID.
     *
     * @param id O ID do produto a ser deletado.
     */
    public void deletarProduto(Long id) {
        repository.deleteById(id);
    }
}