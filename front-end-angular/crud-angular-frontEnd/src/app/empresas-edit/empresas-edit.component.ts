import { Empresas } from './../_model/empresas';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-empresas-edit',
  templateUrl: './empresas-edit.component.html',
  styleUrls: ['./empresas-edit.component.css']
})
export class EmpresasEditComponent implements OnInit{

  public editableEmpresa!: Empresas;

  public actionName: string = 'Editar';

  constructor(public dialogRef: MatDialogRef<EmpresasEditComponent>,
    @Inject(MAT_DIALOG_DATA) dialogData: any){
      if(dialogData.editableEmpresa != null){
        this.editableEmpresa = dialogData.editableEmpresa;
      }

      if(dialogData.actionName != null){
        this.actionName = dialogData.actionName;
      }
    };

  ngOnInit(): void {

  }

  public closeModalWindow($event: any){
    //TODO: handle action - save/cancel
    this.dialogRef.close();
  }

}
