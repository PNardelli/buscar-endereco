package br.app.code.smart.Endereco.service.impl;

import br.app.code.smart.Endereco.model.Cliente;
import br.app.code.smart.Endereco.model.ClienteRepository;
import br.app.code.smart.Endereco.model.Endereco;
import br.app.code.smart.Endereco.model.EnderecoRepository;
import br.app.code.smart.Endereco.service.ClienteService;
import br.app.code.smart.Endereco.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarCliente(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> atualizarCliente = clienteRepository.findById(id);
        if (atualizarCliente.isPresent()){
            salvarCliente(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);

    }


    private void salvarCliente(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        //Verifica se o endereço já possui no banco de dados.
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            //Caso o endereço não seja encontrado, iremos consumuir da API de https://viacep.com.br
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        //Inserir o endereco no cliente
        cliente.setEndereco(endereco);
        //Inserir o cliente no banco de dados.
        clienteRepository.save(cliente);
    }
}
