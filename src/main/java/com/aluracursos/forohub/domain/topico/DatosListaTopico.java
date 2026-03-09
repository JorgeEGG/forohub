package com.aluracursos.forohub.domain.topico;

import java.time.LocalDateTime;

public record DatosListaTopico(
    Long id,
    String titulo,
    String mensaje,
    LocalDateTime fechaCreacion,
    String status,
    String autor,
    String curso
) {

    public DatosListaTopico(Topico topico) {
        this(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensaje(),
            topico.getFechaCreacion(),
            topico.getStatus(),
            topico.getAutor(),
            topico.getCurso()
        );
    }
    
}
