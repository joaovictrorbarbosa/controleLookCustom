package com.example.novo_spring_boot.controller;

import com.example.novo_spring_boot.model.Produto;
import com.example.novo_spring_boot.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Endpoints para gerenciamento do catálogo e estoque de produtos por usuário")
public class ProdutoController {
    
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Lista todos os produtos", description = "Retorna todos os produtos vinculados ao usuário autenticado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - Requer Token JWT válido")
    })
    @GetMapping()
    public List<Produto> listarProdutos(@Parameter(hidden = true) Authentication authentication) {
        // Passamos o username do token para o service filtrar
        return produtoService.listarProdutosPorUsuario(authentication.getName());
    }
    
    @Operation(summary = "Busca produto por ID", description = "Busca os detalhes de um produto específico do usuário pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProduto(@PathVariable Long id, 
        @Parameter(hidden = true) Authentication authentication) {
        Produto produto = produtoService.obterProdutoPorIdEUsuario(id, authentication.getName());
        return ResponseEntity.ok(produto);
    }

    @Operation(summary = "Cadastra um novo produto", description = "Salva um novo produto e o vincula automaticamente ao usuário logado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados do produto inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PostMapping()
    public Produto salvarProduto(@RequestBody Produto produto, 
        @Parameter(hidden = true) Authentication authentication){
        // Vinculamos o produto ao usuário logado antes de salvar
        return produtoService.salvarProdutoComUsuario(produto, authentication.getName());
    }
    
    @Operation(summary = "Deleta um produto", description = "Remove um produto do estoque pelo ID (somente se pertencer ao usuário).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso (sem conteúdo)"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id, 
        @Parameter(hidden = true) Authentication authentication){
        produtoService.excluirProdutoPorIdEUsuario(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca produtos por nome", description = "Filtra os produtos pelo nome informado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
        @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado com o nome informado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/buscarPorNome")
    public ResponseEntity<List<Produto>> buscarProdutoPorNome(@RequestParam String nome, 
        @Parameter(hidden = true) Authentication authentication) {
        List<Produto> produtos = produtoService.findByNomeEUsuario(nome, authentication.getName());
        return produtos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(produtos);
    }
    
    @Operation(summary = "Busca produtos por tamanho", description = "Filtra os produtos pelo tamanho informado (Ex: P, M, G).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
        @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado com o tamanho informado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/buscarPorTamanho")
    public ResponseEntity<List<Produto>> buscarProdutoPorTamanho(@RequestParam String tamanho, 
        @Parameter(hidden = true) Authentication authentication) {
        List<Produto> produtos = produtoService.findByTamanhoEUsuario(tamanho, authentication.getName());
        return produtos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(produtos);
    }
    
    @Operation(summary = "Atualiza um produto", description = "Atualiza as informações de um produto existente pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto produto,
        @Parameter(hidden = true) Authentication authentication) {
        return produtoService.atualizarProdutoPorUsuario(id, produto, authentication.getName());
    }
}