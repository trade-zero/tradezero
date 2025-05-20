import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { Table, TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { TooltipModule } from 'primeng/tooltip';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { InputMaskModule } from 'primeng/inputmask';
import { HoverDirective } from '@shared/directives/hover/hover.directive';
import { DynamicTableItemComponent } from '@shared/components/dynamic-table-custom/dynamic-table-item/dynamic-table-item.component';

@Component({
  selector: 'app-dynamic-table',
  standalone: true,
  imports: [
    ButtonModule,
    CommonModule,
    IconFieldModule,
    InputMaskModule,
    InputIconModule,
    InputTextModule,
    ReactiveFormsModule,
    RouterLink,
    TableModule,
    TooltipModule,
    HoverDirective,
    DynamicTableItemComponent
  ],
  templateUrl: './dynamic-table.component.html',
  styleUrl: './dynamic-table.component.scss'
})
export class DynamicTableComponent implements OnInit {

  cols!: TableColumModel[];
  data!: any[];
  globalFilterFields!: string[];
  searchValue: string | undefined;
  filterControl = new FormControl('');

  @ViewChild('dt1') dt1!: Table;

  @Input() title!: string | undefined;

  @Input() showAddButton: boolean = true;

  constructor(
    public router: Router,
  ) {
  }

  @Input() set columns(cols: TableColumModel[]) {
    this.cols = cols;
  }

  @Input() set dataRows(data: any[]) {
    this.data = data;
  }

  @Input() set filters(data: string[]) {
    this.globalFilterFields = data;
  }

  ngOnInit(): void {
    this.checkInitColum();
    this.observerFilterChange();
  }

  clear(table: Table) {
    table.clear();
    this.searchValue = ''
  }

  checkInitColum(): void {
    if (!this.cols) {
      console.warn('As colunas (cols) não foram fornecidas para a tabela dinâmica.');
    }
    if (!this.data) {
      console.warn('Os dados (data) não foram fornecidos para a tabela dinâmica.');
    }
  }

  observerFilterChange() {
    this.filterControl.valueChanges.subscribe(value => {
      this.dt1.filterGlobal(value, 'contains');
    });
  }
}
