package com.algaworks.vinhos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.vinhos.model.Actor;
import com.algaworks.vinhos.model.TipoVinho;
import com.algaworks.vinhos.model.Vinho;
import com.algaworks.vinhos.repository.Actors;
import com.algaworks.vinhos.repository.Vinhos;

@Controller
@RequestMapping("/ator")
public class AtorCrontroler {
	
	@Autowired
	private Vinhos vinhos;
	
	@Autowired
	private Actors atores;
	
	
	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		vinhos.delete(id);
		
		attributes.addFlashAttribute("mensagem", "Vinho removido com sucesso!");
		
		return "redirect:/vinhos";
	}
	
	/*
	@GetMapping
	public ModelAndView listar() {
		
		ModelAndView modelAndView = new ModelAndView("vinhos/lista-ator");
		modelAndView.addObject("atores", atores.findAll());
	/*
		int page = 2;
		int linesPerPage = 5;
		String direction = "ASC";
		String orderBy = "firstName";
		PageRequest pageRequestw = new PageRequest(page , linesPerPage , Direction.valueOf(direction ));
		
		modelAndView.addObject("atores", atores.findAll(pageRequestw));
		
		
		return modelAndView;
	}
	*/
	/*
	@RequestMapping("Page request")
	public String buscarTodos( Model model, Pageable pegeable){
		 model.addAttribute("atores",atores.findAll(pegeable));
		 return "vinhos/lista-ator";
		
	}
	*/
	
		
	@ModelAttribute("totalp")
	public long contartotalp() {
		Page<Actor> users = atores.findAll(new PageRequest(0, 10));	
		return users.getTotalPages();	
	}
	@ModelAttribute("patual")
	public long contarpatual() {
		Page<Actor> users = atores.findAll(new PageRequest(0, 10));	
		return users.getNumber();	
	}
	
	
	@GetMapping
	public ModelAndView listar(@RequestParam(defaultValue = "0") int pagina, 
	                           @RequestParam(defaultValue = "10") int porPagina,
	                           @RequestParam(defaultValue = "actorId") String[] ordenacao,
	                           @RequestParam(defaultValue = "ASC") Sort.Direction direcao){
		
		ModelAndView modelAndView = new ModelAndView("vinhos/lista-ator");
				
//		Sort sort = new Sort(Sort.Direction.ASC, "actorID" );
//		PageRequest pageRequest = new PageRequest(pagina, 10,sort);
//		Page<Actor> users = atores.findAll(pageRequest);
		
		
		Page<Actor> users = atores.findAll(new PageRequest(pagina, porPagina));		
	    	
		int totalp = users.getTotalPages();		
		modelAndView.addObject("totalp", totalp);
		int patual = users.getNumber();
		modelAndView.addObject("patual", patual);
		boolean uti= users.hasNext();
		
		//System.out.println("toalp"+totalp);
		modelAndView.addObject("atores",users);
		return modelAndView;
	}
	
	
	@GetMapping("/listaordenado")
	public ModelAndView  pesquisarordenado(
	      @RequestParam(defaultValue = "firstName") String ordenacao,
	      @RequestParam(defaultValue = "ASC") Sort.Direction  direcao) {
		ModelAndView modelAndView = new ModelAndView("vinhos/lista-ator");
	return modelAndView.addObject("atores" , atores.findAll(new Sort(direcao, ordenacao)));
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
		public ModelAndView buscar(Actor actor) {
			ModelAndView modelAndView = new ModelAndView("vinhos/busca-ator");
			
			modelAndView.addObject(actor);
					
			return modelAndView;
		}
	  
		@RequestMapping("/buscar2")
		public String form() {
		return "vinhos/busca-ator2";
		}
	  
	@PostMapping("/busca")
	public ModelAndView listarnome(Actor actor,  Pageable pageRequest) {
		ModelAndView modelAndView = new ModelAndView("vinhos/lista-ator");
		String nome = actor.getFirstName();
		/*
		int page = 1;
		int linesPerPage = 3;
		String direction = "ASC";
		String orderBy = "firstName";
		PageRequest pageRequestw = new PageRequest(page , linesPerPage , Direction.valueOf(direction ), orderBy );
		modelAndView.addObject("atores", atores.findByFirstNameContainingIgnoreCaseOrderByFirstName(nome,  pageRequestw));
		*/
		
		modelAndView.addObject("atores", atores.findByFirstNameContainingIgnoreCaseOrderByFirstName(nome));
		
		return modelAndView;
	
		
		/*
		 * public Page<Cliente> buscarCliente(String nome, Integer vendedor,  Pageable pageRequest) {


/*
* page se refere a página de paginação que a consulta retornará o valor (Int)
*linesPerPage = quantidade de objetos que irá retornar em cada página
* direction = Se vai ordenar de forma crescente ou não ASC ou DESC
*orderBy = trata-se do campo utilizado na ordenação

PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);

Page<Cliente> clientes = clienteRepository.findByNomeStartingWithAndVendedorEquals(String nome, Integer vendedor,  pageRequest);
} 
		 */
	}
	
	@RequestMapping("/busca2")
	public ModelAndView findNome (Actor actor) {
		//Pageable pag = new PageRequest(0, 10);
		
		
		String nome = actor.getFirstName();
		ModelAndView modelAndView = new ModelAndView("vinhos/lista-ator");
		//modelAndView.addObject("atores", atores.findByFirstNameContainingIgnoreCaseOrderByFirstName(nome,pag));
		
		Page<Actor> users = atores.findByFirstNameContainingIgnoreCaseOrderByFirstName(nome,new PageRequest(0, 10));	
		
		int totalp = users.getTotalPages();		
		modelAndView.addObject("totalp", totalp);
		int patual = users.getNumber();
		modelAndView.addObject("patual", patual);
		modelAndView.addObject("atores", users);
		
		return modelAndView;
	}
}
