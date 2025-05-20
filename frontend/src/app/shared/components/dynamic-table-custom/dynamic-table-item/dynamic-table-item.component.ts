import { Component, Input } from '@angular/core';
import { ActiveStatusPipe } from '@shared/pipes/active-status-pipe/active-status.pipe';
import { CpfPipe } from '@shared/pipes/cpf-pipe/cpf.pipe';
import { CnpjPipe } from '@shared/pipes/cnpj-pipe/cnpj.pipe';
import { PhonePipe } from '@shared/pipes/phone-pipe/phone.pipe';
import { UserStatusPipe } from '@shared/pipes/user-status-pipe/user-status.pipe';
import { CommonModule, DatePipe } from '@angular/common';
import { Clipboard } from '@angular/cdk/clipboard';
import { NotificationService } from '@shared/components/notification/shared/notification.service';
import { ButtonModule } from 'primeng/button';
import { TooltipModule } from 'primeng/tooltip';
import { Router, RouterModule } from '@angular/router';
import { TableColumModel } from '@shared/models/table-colum.model';
import { ColumnTypeEnum } from '@shared/enum/table-column-value.enum';
import { ColumnPipeEnum } from '@shared/enum/table-column-pipe.enum';

@Component({
  selector: 'app-dynamic-table-item',
  standalone: true,
  imports: [
    CommonModule,
    ButtonModule,
    TooltipModule,
    RouterModule,

    ActiveStatusPipe,
    CpfPipe,
    CnpjPipe,
    PhonePipe,
    UserStatusPipe
  ],
  providers: [
    ActiveStatusPipe,
    CpfPipe,
    CnpjPipe,
    PhonePipe,
    UserStatusPipe,
    DatePipe
  ],
  templateUrl: './dynamic-table-item.component.html',
  styleUrl: './dynamic-table-item.component.scss'
})
export class DynamicTableItemComponent {

  @Input() set column(value: TableColumModel) { this.col = value; }
  @Input() set row(value: any) { this.rowData = value; }

  columnPipe = ColumnPipeEnum;
  columnType = ColumnTypeEnum;

  rowData!: any;
  col!: TableColumModel;

  constructor(
    public router: Router,
    private clipboard: Clipboard,
    private notificationService: NotificationService,
    private cpfPipe: CpfPipe,
    private cnpjPipe: CnpjPipe,
    private phonePipe: PhonePipe,
    private datePipe: DatePipe
  ) {
  }

  getNestedValue(data: any, field: string): any {
    return field.split('.')
      .reduce((obj, key) => (obj && obj[key] !== undefined)
        ? obj[key]
        : '-', data);
  }

  getCellValue(rowData: any, col: any): string {
    const rawValue = this.getRawValue(rowData, col.field);

    switch(col.columnType) {
      case this.columnPipe.CPF:
        return this.cpfPipe.transform(rawValue);
      case this.columnPipe.CNPJ:
        return this.cnpjPipe.transform(rawValue);
      case this.columnPipe.PHONE:
        return this.phonePipe.transform(rawValue);
      case this.columnPipe.DATE:
        return this.datePipe.transform(rawValue, 'dd/MM/yyyy')!;
      case this.columnPipe.TIME:
        return this.datePipe.transform(rawValue, 'HH:mm')!;
      // Adicione outros casos conforme necessário
      default:
        return rawValue?.toString() || '';
    }
  }

  private getRawValue(obj: any, fieldPath: string): any {
    return fieldPath.split('.').reduce((o, i) => o?.[i], obj);
  }

  copyToClipboard(value: string): void {
    if (!value?.trim()) {
      this.notificationService.info('Copiar','Nada para copiar');
      return;
    }

    if (this.clipboard.copy(value)) {
      this.notificationService.success('Copiar','Copiado para área de transferência');
    } else {
      this.notificationService.warn('Copiar','Falha ao copiar');
    }
  }

  onButtonFocus(event: Event): void {
    (event.currentTarget as HTMLElement).classList.add('opacity-100');
  }

  onButtonBlur(event: Event): void {
    (event.currentTarget as HTMLElement).classList.remove('opacity-100');
  }

  protected readonly ColumnTypeEnum = ColumnTypeEnum;
}
