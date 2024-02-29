package com.example.miproyecto.services;

import com.example.miproyecto.entities.Comic;
import com.example.miproyecto.entities.Pedido;
import com.example.miproyecto.repositories.ComicRepository;
import com.example.miproyecto.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    @Transactional
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public Optional<Pedido> update(Long id, Pedido pedido) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedidoDb = pedidoOptional.get();
            pedidoDb.setFecha(pedido.getFecha());
            pedidoDb.setPrecio(pedido.getPrecio());
            pedidoDb.setUsuario(pedido.getUsuario());
            pedidoDb.setComic(pedido.getComic());
            return Optional.of(pedidoRepository.save(pedidoDb));
        }
        return pedidoOptional;
    }

    @Override
    @Transactional
    public Optional<Pedido> delete(Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        pedidoOptional.ifPresent(pedidoDb -> pedidoRepository.delete(pedidoDb));
        return pedidoOptional;
    }
}
