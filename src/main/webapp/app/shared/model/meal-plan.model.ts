import { IRecipeServing } from 'app/shared/model/recipe-serving.model';

export interface IMealPlan {
  id?: number;
  name?: string;
  recipes?: IRecipeServing[];
}

export const defaultValue: Readonly<IMealPlan> = {};
