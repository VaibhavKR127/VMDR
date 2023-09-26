package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Assets;

public interface AssetsRepo extends JpaRepository<Assets, String> {

}
