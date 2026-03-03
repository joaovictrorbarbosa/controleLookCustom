package com.example.novo_spring_boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.novo_spring_boot.exception.RecursoNaoEncontradoException;
import com.example.novo_spring_boot.model.Produto;
import com.example.novo_spring_boot.repository.ProdutoRepository;

@Service
public class ProdutoService {
    public final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }  
    
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto obterProdutoPorId(Long id) {
        return produtoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Produto com id " + id + " não encontrado"));
    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void excluirProduto(Long id) {
        if(!produtoRepository.existsById(id)){
            throw new RecursoNaoEncontradoException("Produto com id " + id + " não encontrado");
        }
        produtoRepository.deleteById(id);
    }

    public List<Produto> findByNome(String nome) {
        return produtoRepository.findByNome(nome);
    }

    
	public Produto atualizarProduto( Long id, Produto produto) {
		if (produtoRepository.existsById(id)) {
			produto.setId(id);
			return produtoRepository.save(produto);
		} else {
			throw new RuntimeException("Produto não encontrado.");
		}
	}

    public List<Produto> findByTamanho(String tamanho) {
        return produtoRepository.findByTamanho(tamanho);
    }
}
