package edu.mx.unsis.unsiSmile.mappers;

import java.util.List;

public interface BaseMapper<D, E> {
    E toEntity(D dto);
    D toDto(E entity);
    List<D> toDtos(List<E> entities);
}
