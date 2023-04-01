import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fornecedores'
})
export class FornecedoresPipe implements PipeTransform {

  transform(value: string): string {
    switch(value.toUpperCase()){
      case 'J': return 'Pessoa Júridica';
      case 'F': return 'Pessoa Fisica';
    }
    return '';
  }

}
