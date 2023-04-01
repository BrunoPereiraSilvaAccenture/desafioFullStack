import { Component, Inject, OnInit } from '@angular/core';
import { Fornecedores } from '../_model/fornecedores';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-fornecedores-edit',
  templateUrl: './fornecedores-edit.component.html',
  styleUrls: ['./fornecedores-edit.component.css']
})
export class FornecedoresEditComponent implements OnInit{
  public editableFornecedor!: Fornecedores;

  public actionName: string = 'Editar';

  constructor(public dialogRef: MatDialogRef<FornecedoresEditComponent>,
    @Inject(MAT_DIALOG_DATA) dialogData: any){
      if(dialogData.editableFornecedor != null){
        this.editableFornecedor = dialogData.editableFornecedor;
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
