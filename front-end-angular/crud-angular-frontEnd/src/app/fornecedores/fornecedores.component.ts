import { Component, OnInit } from '@angular/core';
import { Fornecedores } from '../_model/fornecedores';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { FornecedoresService } from '../services/fornecedores.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DialogComponent } from '../dialog/dialog.component';
import { HttpErrorResponse } from '@angular/common/http';
import { FornecedoresEditComponent } from '../fornecedores-edit/fornecedores-edit.component';

@Component({
  selector: 'app-fornecedores',
  templateUrl: './fornecedores.component.html',
  styleUrls: ['./fornecedores.component.css']
})
export class FornecedoresComponent implements OnInit{
  public displayedColumns: string[] = ['idFornecedor','empresaIdEmpresa','tipodocumento','cnpjcpf','rg','datanascimento', 'nome','email','endereco','cidade','uf','cep','telefone', 'actions'];
  //public dataSource!: Observable<Empresas[]>;

  dataSource = new MatTableDataSource<Fornecedores>();

  constructor(private dialog: MatDialog,private fornecedorService:FornecedoresService,
    private _snackBar: MatSnackBar){};

  ngOnInit(): void {

    this.fornecedorService.listFornecedores().subscribe(things => {
      this.dataSource.data = things;
  });

 
    //this.dataSource = this.empresasService.listEmpresas();
  }

  public editEmpresa(inputFornecedores:Fornecedores){
    this.dialog.open(FornecedoresEditComponent,{disableClose: true,
      data: {editableFornecedor: inputFornecedores}}).
        afterClosed().subscribe(
      resp => {
        console.log(`Editar fornecedor ${inputFornecedores.nome.toUpperCase()}!`);
    });
  }

  public deleteEmpresa(inputFornecedores:Fornecedores){
    this.dialog.open(DialogComponent,{disableClose: true,
      data: {
        dialogMsg:`Tem certeza que deseja excluir a fornecedor ${inputFornecedores.nome.toUpperCase()}?`,
        leftButtonLabel:'Cancelar',
        rightButtonLabel:'Sim'}}).
        afterClosed().subscribe(
      resp => {
        if(resp) {
          console.log(`Fornecedor ${inputFornecedores.nome.toUpperCase()} excluida com sucesso!`);
          this.fornecedorService.delete(inputFornecedores.idFornecedor).subscribe(
              (resp)=> {
                this._snackBar.open(`Fornecedor ${inputFornecedores.nome} Deletado com Sucesso!`,'',{duration: 10000});
                window.location.reload();
              },
              (error:HttpErrorResponse)=>
              {
                this._snackBar.open(`Status: ${error.status}-${error.statusText}; Message: ${error.error}`,'',{duration: 10000});
                return null;
              });
        }
        else {console.log(`Fornecedor ${inputFornecedores.nome.toUpperCase()} não excluido!`);}
    });
  }

  public createNewEmpresa(){
    this.dialog.open(FornecedoresEditComponent,{disableClose: true,
      data: {actionName: 'Criar'}}).
        afterClosed().subscribe(
      resp => {
        console.log('Criar empresa!');
    });
  }

  applyFilter(event: Event) {

    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}
