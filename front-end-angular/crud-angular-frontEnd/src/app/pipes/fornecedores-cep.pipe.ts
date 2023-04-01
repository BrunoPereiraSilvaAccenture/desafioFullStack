import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fornecedoresCep'
})
export class FornecedoresCepPipe implements PipeTransform {

  transform(value: string|number): string {
        let valorFormatado = value + '';
    
        valorFormatado = valorFormatado
            .padStart(8, '0')                  // item 1
            .substr(0, 8)                      // item 2
            .replace(/[^0-9]/, '')              // item 3
            .replace(                           // item 4
                /(\d{2})(\d{3})(\d{3})/,
                '$1.$2-$3'
            );
    
        return valorFormatado;
    
        
    }

}
