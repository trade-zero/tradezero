import { AfterViewInit, Component, Input, OnChanges, OnInit, SimpleChanges, TemplateRef, ViewChild } from '@angular/core';
import { TableColumModel } from '@shared/models/table-colum.model';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Table, TableModule } from 'primeng/table';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { TooltipModule } from 'primeng/tooltip';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { DynamicTableItemComponent } from '@shared/components/dynamic-table-custom/dynamic-table-item/dynamic-table-item.component';
import { HoverDirective } from '@shared/directives/hover/hover.directive';

@Component({
  selector: 'app-dynamic-table-expansive',
  standalone: true,
  imports: [
    TableModule,
    CommonModule,
    ButtonModule,
    TooltipModule,
    IconFieldModule,
    InputIconModule,
    InputTextModule,
    ReactiveFormsModule,
    RouterLink,
    DynamicTableItemComponent,
    HoverDirective
  ],
  templateUrl: './dynamic-table-expansive.component.html',
  styleUrl: './dynamic-table-expansive.component.scss'
})
export class DynamicTableExpansiveComponent implements OnInit, OnChanges, AfterViewInit {

  cols!: TableColumModel[];
  colsExpanded!: TableColumModel[];
  data!: any[];
  expandedFieldItem: string = '';
  expandedRows = {};
  filterControl = new FormControl('');
  globalFilterFields!: string[];
  searchValue: string | undefined;
  @ViewChild('dt1') dt1!: Table;

  @Input() title!: string | undefined;

  @Input() titleTableExpanded!: string | undefined;

  @Input() showAddButton: boolean = true;

  @Input() showAddButtonSecondTable: boolean = true;

  @Input() set columns(cols: TableColumModel[]) { this.cols = cols; }

  @Input() set columnsExpanded(cols: TableColumModel[]) { this.colsExpanded = cols; }

  @Input() set dataRows(data: any[]) { this.data = data; }

  @Input() set filters(data: string[]) { this.globalFilterFields = data; }

  @Input() set expandedField(data: string) { this.expandedFieldItem = data; }

  @Input() expandedContent: any; // Adicione esta linha
  @Input() expandedRow: any; // E esta linha

  @Input() customTemplate!: TemplateRef<any>;
  customComponent!: TemplateRef<any>;

  constructor(
    public router: Router
  ) {}

  ngOnInit(): void {
    this.checkInitColum();
    this.observerFilterChange();
    console.log(this.data);
  }

  ngAfterViewInit(): void {
    this.customComponent = this.customTemplate;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['cols'] && !changes['cols'].firstChange) {
      console.log('Colunas atualizadas:', this.cols);
    }
    if (changes['data'] && !changes['data'].firstChange) {
      console.log('Dados atualizados:', this.data);
    }
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


  onRowExpand(event: any) {
    console.log('Row expanded:', event.data);
  }

  onRowCollapse(event: any) {
    console.log('Row collapsed:', event.data);
  }

}
