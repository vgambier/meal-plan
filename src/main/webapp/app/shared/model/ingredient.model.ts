export interface IIngredient {
  id?: number;
  name?: string;
  substitutes?: string | null;
}

export const defaultValue: Readonly<IIngredient> = {};
