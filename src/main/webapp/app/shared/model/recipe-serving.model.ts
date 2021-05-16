import { IRecipe } from 'app/shared/model/recipe.model';
import { IMealPlan } from 'app/shared/model/meal-plan.model';

export interface IRecipeServing {
  id?: number;
  servingsOverride?: number | null;
  recipe?: IRecipe;
  mealPlan?: IMealPlan | null;
}

export const defaultValue: Readonly<IRecipeServing> = {};
