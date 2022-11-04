package com.example.core.services;

/**
 * Интерфейс определяет основные методы работы с хранилищами данных.
 * @param <T>
 */
public interface DataFileService <T>{

    boolean putObject(T obj);

    Object getObject (String value);
}
