package com.example.novo_spring_boot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.novo_spring_boot.exception.RecursoNaoEncontradoException;
import com.example.novo_spring_boot.model.Produto;
import com.example.novo_spring_boot.repository.ProdutoRepository;


@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;
    
    
    @Test
    @DisplayName("Deve salvar um produto com sucesso")
    void testSalvarProduto() {
            Produto produto = new Produto("Teste",10.50,3,"GG");
            Produto produtoSalvo = new Produto("Teste",10.50,3,"GG");

            //Mock
            when(produtoRepository.save(any(Produto.class))).thenReturn(produtoSalvo);

        //ACT
        Produto resultado = produtoService.salvarProduto(produto);

        assertNotNull(resultado);
        assertEquals("Teste", resultado.getNome());
        assertEquals(10.50, resultado.getPreco());
        
        // Verifica se o método save foi chamado exatamente 1 vez
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    @DisplayName("Deve buscar um produto por seu ID")
    void testBuscarProdutoPorIdSucess(){
        //Arrange
        Long id =1L;
        Produto produtoteste = new Produto(id,"Café Pelé", 12.69);
        
        //Mock
        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoteste));

        //ACT
        Produto resultado = produtoService.obterProdutoPorId(id);

        //Asserts
        assertNotNull(resultado);
        assertEquals("Café Pelé", resultado.getNome());

        verify(produtoRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve lançar excessao quando ID não existir")
    void testBuscarPorIdFalha(){
        //Arrange
        Long id = 2L;

        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, ()-> {
            produtoService.obterProdutoPorId(id);
        });

        verify(produtoRepository,times(1)).findById(id);
    }


}
