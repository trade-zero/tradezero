import { ColumnPipeValue } from '@shared/enum/table-column-pipe.enum';
import { ColumnTypeValue } from '@shared/enum/table-column-value.enum';
import { TemplateRef } from '@angular/core';

export class TableColumModel {

    field?: string;
    header?: string;
    columnType?: ColumnTypeValue | 'DEFAULT';
    pipeType?: ColumnPipeValue;
    buttons?: TableColumButtonModel[];
    customTemplate?: TemplateRef<any>;

    constructor(props: Partial<TableColumModel> = {}) {
        this.columnType = props.columnType || 'DEFAULT'
        Object.assign(this, props);

    }

}

export class TableColumButtonModel {

    label?: string;
    icon?: string;
    styleClass?: string;
    onClick?: (data?: any, event?: any) => void;
    disabled?: boolean;
    [key: string]: any;

    constructor(props: Partial<TableColumButtonModel> = {}) {
        Object.assign(this, props);
    }

}
