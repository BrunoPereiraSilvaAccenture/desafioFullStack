import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Fornecedores } from '../_model/fornecedores';
import { Endereco } from '../_model/endereco';
import { EnderecoService } from '../services/endereco.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FornecedoresService } from '../services/fornecedores.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Empresas } from '../_model/empresas';
import { Observable } from 'rxjs';
import { EmpresasService } from '../services/empresas.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-fornecedores-form',
  templateUrl: './fornecedores-form.component.html',
  styleUrls: ['./fornecedores-form.component.css'],
})
export class FornecedoresFormComponent implements OnInit {
  @Input() public actionName = 'Editar';

  public name: FormControl = new FormControl('');

  @Output() closeModalEventEmitter: EventEmitter<boolean> =
    new EventEmitter<boolean>();

  public fornecedorForm!: FormGroup;

  public isFormReady = false;

  public inputDisabled: boolean = true;

  @Input() public editableFornecedor!: Fornecedores;

  public empresaList!: Empresas[];

  public enderecoGet: Endereco | undefined;

  public tipoDocLis = [
    {nome:'Pessoa Fisica',sigla:'F'},
    {nome:'Pessoa Juridica',sigla:'J'}
  ];

  public rgTemp!:string;
  public DNTemp!:Date;

  constructor(
    private formBuilder: FormBuilder,
    private enderecoService: EnderecoService,
    private _snackBar: MatSnackBar,
    private fornecedoresService: FornecedoresService,
    private empresasService:EmpresasService
  ) {}

  ngOnInit(): void {

    this.empresasService.listEmpresas().subscribe(things => {
      this.empresaList = things;
  });
    this.fornecedorForm = this.formBuilder.group({
      idFornecedor: [
        this.editableFornecedor != null
          ? this.editableFornecedor.idFornecedor
          : '',
      ],
      tipodocumento: [
        this.editableFornecedor != null
          ? this.editableFornecedor.tipodocumento
          : '',
        Validators.required,
      ],
      nome: [
        this.editableFornecedor != null ? this.editableFornecedor.nome : '',
        Validators.required,
      ],
      cnpjcpf: [
        this.editableFornecedor != null ? this.editableFornecedor.cnpjcpf : '',
        [
          Validators.required,
          Validators.minLength(11),
          Validators.pattern('[0-9]+'),
        ],
      ],
      cep: [
        this.editableFornecedor != null ? this.editableFornecedor.cep : '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern('[0-9]+'),
        ],
      ],
      endereco: [
        this.editableFornecedor != null ? this.editableFornecedor.endereco : '',
        Validators.required,
      ],
      uf: [
        this.editableFornecedor != null ? this.editableFornecedor.uf : '',
        Validators.required,
      ],
      cidade: [
        this.editableFornecedor != null ? this.editableFornecedor.cidade : '',
        Validators.required,
      ],
      telefone: [
        this.editableFornecedor != null ? this.editableFornecedor.telefone : '',
        [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(11),
          Validators.pattern('[0-9]+'),
        ],
      ],
      rg: [this.editableFornecedor != null ? 
        this.editableFornecedor.tipodocumento == null?'':
        this.rgTemp = this.editableFornecedor.rg : ''],
      datanascimento: [
        this.editableFornecedor != null
          ? this.editableFornecedor.datanascimento == null? '' :
          this.editableFornecedor.tipodocumento == null?'':
          this.DNTemp = new Date(this.editableFornecedor.datanascimento)
          : '',
      ],
      email: [
        this.editableFornecedor != null ? this.editableFornecedor.email : '',
        [Validators.required,Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$')]
      ],      
      idEmpresa: [
        this.editableFornecedor != null
          ? this.editableFornecedor.empresaIdEmpresa
          : null,
        Validators.required,
          
      ],
    });
    this.esconderComponente(this.fornecedorForm.controls['tipodocumento'].value);
    this.fornecedorForm.controls['uf'].disable();
    this.fornecedorForm.controls['cidade'].disable();
    this.fornecedorForm.controls['endereco'].disable();
  }

  public cancel() {
    this.closeModalEventEmitter.emit(false);
  }

 datePipe = new DatePipe('en-US');
 datac: string = '';
  public save() {

        if (this.actionName == 'Editar') {
      
    this.fornecedoresService
      .upadateFornecedor(
        {tipodocumento: this.fornecedorForm.controls['tipodocumento'].value,
        cnpjcpf: this.fornecedorForm.controls['cnpjcpf'].value,
        nome: this.fornecedorForm.controls['nome'].value,
        cep: this.fornecedorForm.controls['cep'].value,
        telefone: this.fornecedorForm.controls['telefone'].value,
          email: this.fornecedorForm.controls['email'].value,
          rg: this.fornecedorForm.controls['rg'].value,
          datanascimento:  this.datePipe.transform(this.fornecedorForm.controls['datanascimento'].value, 'dd/MM/yyyy') as string
        },
        this.fornecedorForm.controls['idFornecedor'].value
      )
      .subscribe(
        (resp) => {
          this._snackBar.open(
            `Fornecedor ${this.fornecedorForm.controls['nome'].value} editado com Sucesso!`,
            '',
            { duration: 5000 }
          );
          this.closeModalEventEmitter.emit(true);
          window.location.reload();
        },
        (error: HttpErrorResponse) => {
          this._snackBar.open(
            `Status: ${error.status}-${error.statusText}; Message: ${error.error}`,
            '',
            { duration: 5000 }
          );
          return null;
        }
      );

    } else {
      this.fornecedoresService
      .saveNewFornecedor({tipodocumento: this.fornecedorForm.controls['tipodocumento'].value,
      cnpjcpf: this.fornecedorForm.controls['cnpjcpf'].value,
      nome: this.fornecedorForm.controls['nome'].value,
      cep: this.fornecedorForm.controls['cep'].value,
      telefone: this.fornecedorForm.controls['telefone'].value,
        email: this.fornecedorForm.controls['email'].value,
        rg: this.fornecedorForm.controls['rg'].value,
        datanascimento:  this.datePipe.transform(this.fornecedorForm.controls['datanascimento'].value, 'dd/MM/yyyy') as string
      },this.fornecedorForm.controls['idEmpresa'].value)
      .subscribe(
        (resp) => {
          this._snackBar.open(
            `Fornecedor ${this.fornecedorForm.controls['nome'].value} Salvo com Sucesso!`,
            '',
            { duration: 5000 }
          );
          this.closeModalEventEmitter.emit(true);
          window.location.reload();
        },
        (error: HttpErrorResponse) => {
          this._snackBar.open(
            `Status: ${error.status}-${error.statusText}; Message: ${error.error}`,
            '',
            { duration: 5000 }
          );
          return null;
        }
      );    
    }
  }


  public buscarEndereco(cep: string) {
    this.enderecoService
      .listEnderdeco(cep)
      .pipe()
      .subscribe(
        (data) => {
          if (data.cep == null) {
            this._snackBar.open('Cep não encontrado', '', { duration: 5000 });
            this.fornecedorForm.controls['cidade'].setValue('');
            this.fornecedorForm.controls['endereco'].setValue('');
            this.fornecedorForm.controls['uf'].setValue('');
          } else {
            this.enderecoGet = data;
            this.fornecedorForm.controls['cidade'].setValue(data.cidade);
            this.fornecedorForm.controls['endereco'].setValue(data.logradouro);
            this.fornecedorForm.controls['uf'].setValue(data.uf);
          }
        },
        (error) => {
          this._snackBar.open('Cep não encontrado', '', { duration: 5000 });
          this.fornecedorForm.controls['cidade'].setValue('');
          this.fornecedorForm.controls['endereco'].setValue('');
          this.fornecedorForm.controls['uf'].setValue('');
        }
      );
  }


  public esconderComponente(tipoDoc:string){

    if(tipoDoc.toUpperCase()=='J'){
      this.fornecedorForm.controls['rg'].setValue('');
      this.fornecedorForm.controls['datanascimento'].setValue(''); 
      this.fornecedorForm.controls['datanascimento'].disable();
      this.fornecedorForm.controls['rg'].disable();
    }
    else if(tipoDoc.toUpperCase()=='F'){
      this.fornecedorForm.controls['rg'].setValue(this.rgTemp);
      this.fornecedorForm.controls['datanascimento'].setValue(this.DNTemp);
      this.fornecedorForm.controls['datanascimento'].enable();
      this.fornecedorForm.controls['rg'].enable();
    }

    
  }

  compareEmpresaObjects(object1: any, object2: any) {
    return object1 && object2 && object1.name == object2.name;
  }

  compareTipodocumentoObjects(object1: any, object2: any) {
    return object1 && object2 && object1.toUpperCase() == object2.toUpperCase();
  }

}
