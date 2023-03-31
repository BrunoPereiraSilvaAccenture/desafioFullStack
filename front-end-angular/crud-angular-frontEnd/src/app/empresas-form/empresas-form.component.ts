import { EmpresasService } from './../services/empresas.service';
import { EnderecoService } from './../services/endereco.service';
import { Empresas } from './../_model/empresas';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Endereco } from '../_model/endereco';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-empresas-form',
  templateUrl: './empresas-form.component.html',
  styleUrls: ['./empresas-form.component.css']
})
export class EmpresasFormComponent implements OnInit{

  @Input() public actionName = 'Editar';

  public name: FormControl = new FormControl('');

  @Output() closeModalEventEmitter: EventEmitter<boolean> = new EventEmitter<boolean>();

  public empresaForm!: FormGroup;

  public isFormReady = false;

  public inputDisabled: boolean = true;

  @Input() public editableEmpresa!: Empresas;

  public enderecoGet:Endereco | undefined;

  constructor(private formBuilder: FormBuilder, private enderecoService:EnderecoService,
    private _snackBar: MatSnackBar ,private empresasService:EmpresasService){

  };

  ngOnInit(): void {

    this.empresaForm = this.formBuilder.group({
      idEmpresa: [this.editableEmpresa != null ? this.editableEmpresa.idEmpresa : '' ],
      nomefantasia: [this.editableEmpresa != null ? this.editableEmpresa.nomefantasia : '' , Validators.required],
      cnpj: [this.editableEmpresa != null ? this.editableEmpresa.cnpj : '' , [Validators.required,Validators.minLength(14),Validators.pattern("[0-9]+")]],
      cep: [this.editableEmpresa != null ? this.editableEmpresa.cep : '' , [Validators.required, Validators.minLength(8),Validators.pattern("[0-9]+")]],
      endereco: [this.editableEmpresa != null ? this.editableEmpresa.endereco : '' , Validators.required],
      uf: [this.editableEmpresa != null ? this.editableEmpresa.uf : '' , Validators.required],
      cidade: [this.editableEmpresa != null ? this.editableEmpresa.cidade : '' , Validators.required],
      telefone: [this.editableEmpresa != null ? this.editableEmpresa.telefone : '' , [Validators.required, Validators.minLength(10),Validators.maxLength(11),Validators.pattern("[0-9]+")]]

    });

  }

  public cancel(){
    console.log('Cancelar clicado');
    this.closeModalEventEmitter.emit(false);
  }

  public save(){
    if(this.actionName == 'Editar'){

      console.log(this.empresaForm.controls['idEmpresa'].value);
      this.empresasService.saveUpdate({
      cnpj:this.empresaForm.controls['cnpj'].value,
      nomefantasia:this.empresaForm.controls['nomefantasia'].value,
      cep:this.empresaForm.controls['cep'].value,
      telefone:this.empresaForm.controls['telefone'].value},
      this.empresaForm.controls['idEmpresa'].value
      ).subscribe(
        (resp)=> {
          this._snackBar.open(`Empresa ${this.empresaForm.controls['nomefantasia'].value} Editada com Sucesso!`,'',{duration: 10000});
          this.closeModalEventEmitter.emit(true);
          window.location.reload();
        },
        (error:HttpErrorResponse)=>
        {
          this._snackBar.open(`Status: ${error.status}-${error.statusText}; Message: ${error.error}`,'',{duration: 10000});
          return null;
        });
    }else{

      this.empresasService.saveNew({
      cnpj:this.empresaForm.controls['cnpj'].value,
      nomefantasia:this.empresaForm.controls['nomefantasia'].value,
      cep:this.empresaForm.controls['cep'].value,
      telefone:this.empresaForm.controls['telefone'].value}).subscribe(
        (resp)=> {
          this._snackBar.open(`Empresa ${this.empresaForm.controls['nomefantasia'].value} Salva com Sucesso!`,'',{duration: 10000});
          this.closeModalEventEmitter.emit(true);
          window.location.reload();
        },
        (error:HttpErrorResponse)=>
        {
          this._snackBar.open(`Status: ${error.status}-${error.statusText}; Message: ${error.error}`,'',{duration: 10000});
          return null;
        });
      }
  }

  public buscarEndereco(cep: string){
    this.enderecoService.listEnderdeco(cep).pipe().subscribe(data => {
      if(data.cep == null){
        this._snackBar.open('Cep não encontrado','',{duration: 5000});
        this.empresaForm.controls['cidade'].setValue('');
        this.empresaForm.controls['endereco'].setValue('');
        this.empresaForm.controls['uf'].setValue('');}
     else{
      this._snackBar.open(data.bairro,'',{duration: 5000});
       this.enderecoGet = data;
       this.empresaForm.controls['cidade'].setValue(data.cidade);
       this.empresaForm.controls['endereco'].setValue(data.logradouro);
       this.empresaForm.controls['uf'].setValue(data.uf);}
  }, error => {
    this._snackBar.open('Cep não encontrado','',{duration: 5000});
    this.empresaForm.controls['cidade'].setValue('');
        this.empresaForm.controls['endereco'].setValue('');
        this.empresaForm.controls['uf'].setValue('');
  });
  }


}
