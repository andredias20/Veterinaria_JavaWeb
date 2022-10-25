package bean;

import dao.AgendamentoDAO;
import dao.TipoAnimalDAO;
import dao.VeterinarioDAO;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import model.Agendamento;
import model.Paciente;
import model.TipoAnimal;
import model.Veterinario;

/**
 *
 * @author andre
 */
@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {

    TipoAnimal tipoSelecionado;
    Veterinario veterinarioSelecionado;
    String nomeTutor;
    String telefonetutor;
    String nomeAnimal;
    String motivo;
    Date horario;
    
    String animalName;
    String animalDescription;
    
    String vetNome;
    int vetTipo;

    @Inject
    TipoAnimalDAO tiposDAO;

    @Inject
    VeterinarioDAO vetDAO;

    @Inject
    AgendamentoDAO agendaDAO;

    LinkedList<SelectItem> veterinarioSelectItems;

    public UserBean() {
    }
    
    public String vetRedirect(){
        return "VetCad.xhtml";
    }
    public String animalRedirect(){
        return "AnimalCad.xhtml";
    }

    public String getVetNome() {
        return vetNome;
    }

    public void setVetNome(String vetNome) {
        this.vetNome = vetNome;
    }

    public int getVetTipo() {
        return vetTipo;
    }

    public void setVetTipo(int vetTipo) {
        this.vetTipo = vetTipo;
    }
    
    

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getAnimalDescription() {
        return animalDescription;
    }

    public void setAnimalDescription(String animalDescription) {
        this.animalDescription = animalDescription;
    }
    
    public String criarAnimal(){
        tiposDAO.addTipoAnimal(new TipoAnimal(animalName, animalDescription));
        return "index.xhtml";
    }
    
    public String criarVeterinario(){
        vetDAO.addVeterinario(new Veterinario(vetNome, vetTipo));
        return "index.xhtml";
    }

    public TipoAnimal getTipoSelecionado() {
        return tipoSelecionado;
    }

    public void setTipoSelecionado(TipoAnimal tipoSelecionado) {

        this.tipoSelecionado = tipoSelecionado;
    }

    public Veterinario getVeterinarioSelecionado() {
        return veterinarioSelecionado;
    }

    public void setVeterinarioSelecionado(Veterinario veterinarioSelecionado) {
        this.veterinarioSelecionado = veterinarioSelecionado;
    }

    public LinkedList<SelectItem> getVeterinarioSelectItems() {

        if (tipoSelecionado == null) {
            veterinarioSelectItems = new LinkedList<>();
            veterinarioSelectItems.add(
                    new SelectItem(
                            null,
                            "Selecione um tipo de Animal"
                    ));
            return veterinarioSelectItems;
        }

        return veterinarioSelectItems
                = vetDAO.getSelectItems(
                        tipoSelecionado.getId());
    }

    public void setVeterinarioSelectItems(LinkedList<SelectItem> veterinarioSelectItems) {
        this.veterinarioSelectItems = veterinarioSelectItems;
    }

    public String getNomeTutor() {
        return nomeTutor;
    }

    public void setNomeTutor(String nomeTutor) {
        this.nomeTutor = nomeTutor;
    }

    public String getTelefonetutor() {
        return telefonetutor;
    }

    public void setTelefonetutor(String telefonetutor) {
        this.telefonetutor = telefonetutor;
    }

    public String getNomeAnimal() {
        return nomeAnimal;
    }

    public void setNomeAnimal(String nomeAnimal) {
        this.nomeAnimal = nomeAnimal;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public void criar() {
        Agendamento agendamento = new Agendamento(
                veterinarioSelecionado.getId(),
                tipoSelecionado.getId(),
                horario,
                new Paciente(nomeAnimal, motivo, nomeTutor, telefonetutor)
        );

        agendaDAO.addAgendamento(agendamento);

    }

    public void limpar() {
        tipoSelecionado = null;
        veterinarioSelecionado = null;
        nomeTutor = null;
        telefonetutor = null;
        nomeAnimal = null;
        motivo = null;
        horario = null;
    }

    public void cancelar(Agendamento e) {
        agendaDAO.removeAgendamento(e);
    }

    @Produces
    VeterinarioDAO instanceVeterinario() {
        if (vetDAO == null) {
            vetDAO = new VeterinarioDAO();
        }
        return vetDAO;
    }

    @Produces
    TipoAnimalDAO instanceTipoAnimal() {
        if (tiposDAO == null) {
            tiposDAO = new TipoAnimalDAO();
        }

        return tiposDAO;
    }

    @Produces
    AgendamentoDAO instanceAgendamentoDAO() {
        if (agendaDAO == null) {
            agendaDAO = new AgendamentoDAO();
        }

        return agendaDAO;
    }
}
