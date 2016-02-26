package br.com.restapi.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.restapi.expceptions.ResourceNotFoundException;
import br.com.restapi.expceptions.ValidationException;
import br.com.restapi.service.UserService;
import br.com.restapi.vo.User;
import javassist.NotFoundException;

/**
 * @author I852136
 *
 */
@RestController
@RequestMapping("/users")
public class UserController extends DefaultController {

	@Autowired
	private UserService userService;

	/**
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<User> findAll(@RequestParam(defaultValue = "0", required = false) int page,
			@RequestParam(defaultValue = "10", required = false) int max, HttpServletRequest req,
			HttpServletResponse res) {
		Page<User> result = userService.findAll(new PageRequest(page, max));
		addPaginationLinks(res, req, result);
		return result.getContent();
	}

	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody User findOne(@PathVariable Long id, HttpServletRequest req) {
		User user = userService.findOne(id);
		if (user == null) {
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.toString(), "Resource not Found",
					req.getRequestURI());
		}
		return user;
	}

	/**
	 * @param user
	 * @param bindingResult
	 * @param req
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody User save(@Valid @RequestBody User user, BindingResult bindingResult,
			HttpServletRequest req) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(HttpStatus.UNPROCESSABLE_ENTITY.toString(), "Problemas de Validação",
					req.getRequestURI(), bindingResult.getFieldErrors());
		}
		return userService.save(user);
	}

	/**
	 * @param id
	 * @param req
	 * @throws NotFoundException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id, HttpServletRequest req) throws ResourceNotFoundException {
		User user = userService.findOne(id);
		if (user != null) {
			userService.delete(user);
		} else {
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.toString(), "Resource not Found",
					req.getRequestURI());
		}
	}

	/**
	 * @param id
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseStatus(code = HttpStatus.OK)
	public @ResponseBody User update(@PathVariable Long id, @Valid @RequestBody User user, BindingResult bindingResult,
			HttpServletRequest req) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(HttpStatus.UNPROCESSABLE_ENTITY.toString(), "Problemas de Validação",
					req.getRequestURI(), bindingResult.getFieldErrors());
		}
		User persistedUser = userService.findOne(id);
		persistedUser.setName(user.getName());
		persistedUser.setLastName(user.getLastName());
		persistedUser.setBio(user.getBio());

		return userService.save(persistedUser);
	}

}
