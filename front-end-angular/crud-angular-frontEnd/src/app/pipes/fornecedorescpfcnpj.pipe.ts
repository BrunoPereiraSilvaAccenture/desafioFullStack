import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fornecedorescpfcnpj'
})
export class FornecedorescpfcnpjPipe implements PipeTransform {

  transform(value: string|number): string {
switch(value.toString().length)
{
  case 11:
    let valorFormatado = value + '';

    valorFormatado = valorFormatado
        .padStart(11, '0')                  // item 1
        .substr(0, 11)                      // item 2
        .replace(/[^0-9]/, '')              // item 3
        .replace(                           // item 4
            /(\d{3})(\d{3})(\d{3})(\d{2})/,
            '$1.$2.$3-$4'
        );

    return valorFormatado;

    case 14:
    let valorFormatado2 = value + '';

    valorFormatado2 = valorFormatado2
        .padStart(14, '0')                  // item 1
        .substr(0, 14)                      // item 2
        .replace(/[^0-9]/, '')              // item 3
        .replace(                           // item 4
            /(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/,
            '$1.$2.$3/$4-$5'
        );

    return valorFormatado2;
    
}
return '';
    
}

}
