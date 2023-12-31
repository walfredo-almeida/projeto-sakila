package com.algaworks.vinhos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.vinhos.model.TipoVinho;
import com.algaworks.vinhos.model.Vinho;
import com.algaworks.vinhos.repository.Vinhos;

@Controller
@RequestMapping("/vinhos")
public class VinhosController {
	
	@Autowired
	private Vinhos vinhos;
	
	 
	
	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		vinhos.delete(id);
		
		attributes.addFlashAttribute("mensagem", "Vinho removido com sucesso!");
		
		return "redirect:/vinhos";
	}
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("vinhos/lista-vinhos");
		modelAndView.addObject("vinhos", vinhos.findAll());
		
		return modelAndView;
	}
	
	@GetMapping("/listaordenado")
	public ModelAndView  pesquisarordenado(
	      @RequestParam(defaultValue = "nome") String ordenacao,
	      @RequestParam(defaultValue = "ASC") Sort.Direction  direcao) {
		ModelAndView modelAndView = new ModelAndView("vinhos/lista-vinhos");
	return modelAndView.addObject("vinhos" , vinhos.findAll(new Sort(direcao, ordenacao)));
	}
	
	
	@GetMapping("/novo")
	public ModelAndView novo(Vinho vinho) {
		ModelAndView modelAndView = new ModelAndView("vinhos/cadastro-vinho");
		
		modelAndView.addObject(vinho);
		modelAndView.addObject("tipos", TipoVinho.values());		
		
		return modelAndView;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Vinho vinho, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(vinho);
		}
		
		vinhos.save(vinho);
		
		attributes.addFlashAttribute("mensagem", "Vinho salvo com sucesso!");
		
		return new ModelAndView("redirect:/vinhos/novo");
	}
	
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		return novo(vinhos.findOne(id));
	}
	

	  @GetMapping("/buscar")
		public ModelAndView buscar(Vinho vinho) {
			ModelAndView modelAndView = new ModelAndView("vinhos/busca-vinho");
			
			modelAndView.addObject(vinho);
			modelAndView.addObject("tipos", TipoVinho.values());		
			
			return modelAndView;
		}
	  
		@RequestMapping("/buscar2")
		public String form() {
		return "vinhos/busca-vinho2";
		}
	  
	@PostMapping("/busca")
	public ModelAndView listarnome(Vinho vinho) {
		ModelAndView modelAndView = new ModelAndView("vinhos/lista-vinhos");
		String nome = vinho.getNome();
		
		modelAndView.addObject("vinhos", vinhos.findByNomeContainingIgnoreCaseOrderByNome(nome));
		
		return modelAndView;
	
	}
	
	@RequestMapping("/busca2")
	public ModelAndView findNome (Vinho vinho) {
		String nome = vinho.getNome();
		ModelAndView modelAndView = new ModelAndView("vinhos/lista-vinhos");
		modelAndView.addObject("vinhos", vinhos.findByNomeContainingIgnoreCaseOrderByNome(nome));
		
		return modelAndView;
	}
}
