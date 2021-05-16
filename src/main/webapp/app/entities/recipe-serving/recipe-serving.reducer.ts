import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRecipeServing, defaultValue } from 'app/shared/model/recipe-serving.model';

export const ACTION_TYPES = {
  FETCH_RECIPESERVING_LIST: 'recipeServing/FETCH_RECIPESERVING_LIST',
  FETCH_RECIPESERVING: 'recipeServing/FETCH_RECIPESERVING',
  CREATE_RECIPESERVING: 'recipeServing/CREATE_RECIPESERVING',
  UPDATE_RECIPESERVING: 'recipeServing/UPDATE_RECIPESERVING',
  PARTIAL_UPDATE_RECIPESERVING: 'recipeServing/PARTIAL_UPDATE_RECIPESERVING',
  DELETE_RECIPESERVING: 'recipeServing/DELETE_RECIPESERVING',
  RESET: 'recipeServing/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRecipeServing>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type RecipeServingState = Readonly<typeof initialState>;

// Reducer

export default (state: RecipeServingState = initialState, action): RecipeServingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RECIPESERVING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RECIPESERVING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RECIPESERVING):
    case REQUEST(ACTION_TYPES.UPDATE_RECIPESERVING):
    case REQUEST(ACTION_TYPES.DELETE_RECIPESERVING):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_RECIPESERVING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RECIPESERVING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RECIPESERVING):
    case FAILURE(ACTION_TYPES.CREATE_RECIPESERVING):
    case FAILURE(ACTION_TYPES.UPDATE_RECIPESERVING):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_RECIPESERVING):
    case FAILURE(ACTION_TYPES.DELETE_RECIPESERVING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECIPESERVING_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RECIPESERVING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RECIPESERVING):
    case SUCCESS(ACTION_TYPES.UPDATE_RECIPESERVING):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_RECIPESERVING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RECIPESERVING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/recipe-servings';

// Actions

export const getEntities: ICrudGetAllAction<IRecipeServing> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RECIPESERVING_LIST,
  payload: axios.get<IRecipeServing>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IRecipeServing> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RECIPESERVING,
    payload: axios.get<IRecipeServing>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IRecipeServing> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RECIPESERVING,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRecipeServing> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RECIPESERVING,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IRecipeServing> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_RECIPESERVING,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRecipeServing> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RECIPESERVING,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
