import { IRecipeServing } from 'app/shared/model/recipe-serving.model';
import { IRecipeIngredient } from 'app/shared/model/recipe-ingredient.model';
import { Season } from 'app/shared/model/enumerations/season.model';

export interface IRecipe {
  id?: number;
  name?: string;
  servings?: number;
  instructions?: string;
  additionalNotes?: string | null;
  pictureContentType?: string | null;
  picture?: string | null;
  source?: string | null;
  season?: Season | null;
  recipeServings?: IRecipeServing[] | null;
  ingredients?: IRecipeIngredient[];
}

export const defaultValue: Readonly<IRecipe> = {};
