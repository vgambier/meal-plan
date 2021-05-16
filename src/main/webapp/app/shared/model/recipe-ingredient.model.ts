import { IIngredient } from 'app/shared/model/ingredient.model';
import { IRecipe } from 'app/shared/model/recipe.model';

export interface IRecipeIngredient {
  id?: number;
  quantity?: number | null;
  unit?: string | null;
  optional?: boolean;
  ingredient?: IIngredient;
  recipe?: IRecipe | null;
}

export const defaultValue: Readonly<IRecipeIngredient> = {
  optional: false,
};
