import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.atividade16.demo.Classes.Produto;
import com.atividade16.demo.Classes.ProdutoRepository;
import com.atividade16.demo.Classes.ProdutoService;

/**
 * Classe de teste para o serviço ProdutoService.
 * Esta classe utiliza Mockito para simular o repositório e testar a lógica de negócios do serviço.
 */
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService service;

    @Mock
    private ProdutoRepository repository;

    public ProdutoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa a criação de um novo produto.
     * Verifica se o produto é salvo corretamente no repositório e se os dados retornados estão corretos.
     */
   @Test
public void testCriarProdutoErro() {
    Produto produto = new Produto();
    produto.setNome("Produto Teste");
    
    when(repository.save(produto)).thenThrow(new RuntimeException("Erro ao criar produto"));

    Exception exception = assertThrows(RuntimeException.class, () -> {
        service.criarProduto(produto);
    });

    assertEquals("Erro ao criar produto", exception.getMessage());
}

@Test
public void testBuscarProdutoPorIdNaoEncontrado() {
    Long id = 1L;

    when(repository.findById(id)).thenReturn(Optional.empty());

    Optional<Produto> resultado = service.buscarProdutoPorId(id);
    
    assertTrue(resultado.isEmpty());
}

@Test
public void testAtualizarProdutoNaoEncontrado() {
    Long id = 1L;
    
    Produto produtoAtualizado = new Produto();
    produtoAtualizado.setNome("Novo Nome");

    when(repository.findById(id)).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> {
        service.atualizarProduto(id, produtoAtualizado);
    });

    assertEquals("Produto não encontrado", exception.getMessage());
}

@Test
public void testDeletarProdutoNaoEncontrado() {
    Long id = 1L;

    doThrow(new EntityNotFoundException("Produto não encontrado")).when(repository).deleteById(id);

    Exception exception = assertThrows(EntityNotFoundException.class, () -> {
        service.deletarProduto(id);
    });

    assertEquals("Produto não encontrado", exception.getMessage());
}
}

