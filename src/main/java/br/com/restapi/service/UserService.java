package br.com.restapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import br.com.restapi.vo.User;

@Service
public interface UserService extends PagingAndSortingRepository<User, Long>{

	Page<User> findAll(Pageable pageable);
	
	
}
