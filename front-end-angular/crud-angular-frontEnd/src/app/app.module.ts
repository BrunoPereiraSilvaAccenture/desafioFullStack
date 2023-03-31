import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './material.module';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { DialogComponent } from './dialog/dialog.component';
import { EmpresasComponent } from './empresas/empresas.component';
import { EmpresasEditComponent } from './empresas-edit/empresas-edit.component';
import { EmpresasFormComponent } from './empresas-form/empresas-form.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { FornecedoresComponent } from './fornecedores/fornecedores.component';
import { FornecedoresEditComponent } from './fornecedores-edit/fornecedores-edit.component';
import { FornecedoresFormComponent } from './fornecedores-form/fornecedores-form.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    DialogComponent,
    EmpresasComponent,
    EmpresasEditComponent,
    EmpresasFormComponent,
    PageNotFoundComponent,
    FornecedoresComponent,
    FornecedoresEditComponent,
    FornecedoresFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    HttpClientModule
  ],
  providers: [
    {provide: LOCALE_ID, useValue: 'pt-BR'}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
