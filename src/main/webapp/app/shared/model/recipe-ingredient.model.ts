import { IRecipe } from 'app/shared/model/recipe.model';
import { IIngredient } from 'app/shared/model/ingredient.model';

export interface IRecipeIngredient {
  id?: number;
  quantity?: number | null;
  unit?: string | null;
  optional?: boolean;
  recipe?: IRecipe | null;
  ingredient?: IIngredient;
}

export const defaultValue: Readonly<IRecipeIngredient> = {
  optional: false,
};
