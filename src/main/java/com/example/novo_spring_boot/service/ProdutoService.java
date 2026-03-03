package com.example.novo_spring_boot.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.novo_spring_boot.exception.RecursoNaoEncontradoException;
import com.example.novo_spring_boot.model.Produto;
import com.example.novo_spring_boot.model.Usuario;
import com.example.novo_spring_boot.repository.ProdutoRepository;
import com.example.novo_spring_boot.repository.UsuarioRepository; // Certifique-se de ter este repositório

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    public ProdutoService(ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository) {
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }  
    
    public List<Produto> listarProdutosPorUsuario(String username) {
    return produtoRepository.findByUserUsername(username.trim());
}

   public Produto obterProdutoPorIdEUsuario(Long id, String username) {
    return produtoRepository.findByIdAndUserUsername(id, username)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado ou acesso negado."));
    }
    public Produto salvarProdutoComUsuario(Produto produto, String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        produto.setUser(usuario); // Vincula o dono
        return produtoRepository.save(produto);
    }

    public void excluirProdutoPorIdEUsuario(Long id, String username) {
        Produto produto = obterProdutoPorIdEUsuario(id, username);
        produtoRepository.delete(produto);
    }

    public List<Produto> findByNomeEUsuario(String nome, String username) {
        return produtoRepository.findByNomeContainingIgnoreCaseAndUserUsername(nome, username);
    }

    public List<Produto> findByTamanhoEUsuario(String tamanho, String username) {
        return produtoRepository.findByTamanhoAndUserUsername(tamanho, username);
    }

    public Produto atualizarProdutoPorUsuario(Long id, Produto produto, String username) {
        // Verifica se o produto existe e pertence ao usuário antes de atualizar
        Produto existente = obterProdutoPorIdEUsuario(id, username);
        
        produto.setId(id);
        produto.setUser(existente.getUser()); // Mantém o dono original
        return produtoRepository.save(produto);
    }
}


