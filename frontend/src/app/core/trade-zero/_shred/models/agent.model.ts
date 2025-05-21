import { BaseResourceModel } from "@shared/models/base-resource.model";

export class AgentModel extends BaseResourceModel{

    name?: string;

    constructor(props: Partial<AgentModel> = {}) {
        super();
        Object.assign(this, props);
    }

}
