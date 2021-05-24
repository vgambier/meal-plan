import { IMealPlan } from 'app/shared/model/meal-plan.model';
import { IRecipe } from 'app/shared/model/recipe.model';

export interface IRecipeServing {
  id?: number;
  servingsOverride?: number | null;
  mealPlan?: IMealPlan | null;
  recipe?: IRecipe;
}

export const defaultValue: Readonly<IRecipeServing> = {};
