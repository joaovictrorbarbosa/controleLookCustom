package com.example.novo_spring_boot.controller;

import com.example.novo_spring_boot.model.Produto;
import com.example.novo_spring_boot.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "https://lookcustom.vercel.app")
public class ProdutoController {
    
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping()
    public List<Produto> listarProdutos(Authentication authentication) {
        // Passamos o username do token para o service filtrar
        return produtoService.listarProdutosPorUsuario(authentication.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProduto(@PathVariable Long id, Authentication authentication) {
        Produto produto = produtoService.obterProdutoPorIdEUsuario(id, authentication.getName());
        return ResponseEntity.ok(produto);
    }

    @PostMapping()
    public Produto salvarProduto(@RequestBody Produto produto, Authentication authentication){
        // Vinculamos o produto ao usuário logado antes de salvar
        return produtoService.salvarProdutoComUsuario(produto, authentication.getName());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id, Authentication authentication){
        produtoService.excluirProdutoPorIdEUsuario(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscarPorNome")
    public ResponseEntity<List<Produto>> buscarProdutoPorNome(@RequestParam String nome, Authentication authentication) {
        List<Produto> produtos = produtoService.findByNomeEUsuario(nome, authentication.getName());
        return produtos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(produtos);
    }
    
    @GetMapping("/buscarPorTamanho")
    public ResponseEntity<List<Produto>> buscarProdutoPorTamanho(@RequestParam String tamanho, Authentication authentication) {
        List<Produto> produtos = produtoService.findByTamanhoEUsuario(tamanho, authentication.getName());
        return produtos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(produtos);
    }
    
    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto produto, Authentication authentication) {
        return produtoService.atualizarProdutoPorUsuario(id, produto, authentication.getName());
    }
}
