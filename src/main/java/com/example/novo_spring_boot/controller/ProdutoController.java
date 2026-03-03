package com.example.novo_spring_boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.novo_spring_boot.model.Produto;
import com.example.novo_spring_boot.service.ProdutoService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping()
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProduto(@PathVariable Long id) {
            Produto produto = produtoService.obterProdutoPorId(id);
            return ResponseEntity.ok(produto);
    }

    @PostMapping()
    public Produto salvarProduto(@RequestBody Produto produto){
        return produtoService.salvarProduto(produto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id){
        produtoService.excluirProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscarPorNome")
    public ResponseEntity<List<Produto>> buscarProdutoPorNome(@RequestParam String nome) {
        List<Produto> produtos = produtoService.findByNome(nome);
    
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
            }
        return ResponseEntity.ok(produtos); 
    }
    
    @GetMapping("/buscarPorTamanho")
    public ResponseEntity<List<Produto>> buscarProdutoPorTamanho(@RequestParam String tamanho) {
        List<Produto> produtos = produtoService.findByTamanho(tamanho);
    
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
            }
        return ResponseEntity.ok(produtos);
    }
    
    @PutMapping("/{id}")
	public Produto atualizar(@PathVariable Long id, @RequestBody Produto produto) {
		return produtoService.atualizarProduto(id, produto);
	}
    

}
