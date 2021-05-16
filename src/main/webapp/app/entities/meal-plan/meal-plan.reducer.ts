import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMealPlan, defaultValue } from 'app/shared/model/meal-plan.model';

export const ACTION_TYPES = {
  FETCH_MEALPLAN_LIST: 'mealPlan/FETCH_MEALPLAN_LIST',
  FETCH_MEALPLAN: 'mealPlan/FETCH_MEALPLAN',
  CREATE_MEALPLAN: 'mealPlan/CREATE_MEALPLAN',
  UPDATE_MEALPLAN: 'mealPlan/UPDATE_MEALPLAN',
  PARTIAL_UPDATE_MEALPLAN: 'mealPlan/PARTIAL_UPDATE_MEALPLAN',
  DELETE_MEALPLAN: 'mealPlan/DELETE_MEALPLAN',
  RESET: 'mealPlan/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMealPlan>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type MealPlanState = Readonly<typeof initialState>;

// Reducer

export default (state: MealPlanState = initialState, action): MealPlanState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MEALPLAN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MEALPLAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MEALPLAN):
    case REQUEST(ACTION_TYPES.UPDATE_MEALPLAN):
    case REQUEST(ACTION_TYPES.DELETE_MEALPLAN):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_MEALPLAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MEALPLAN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MEALPLAN):
    case FAILURE(ACTION_TYPES.CREATE_MEALPLAN):
    case FAILURE(ACTION_TYPES.UPDATE_MEALPLAN):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_MEALPLAN):
    case FAILURE(ACTION_TYPES.DELETE_MEALPLAN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEALPLAN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEALPLAN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MEALPLAN):
    case SUCCESS(ACTION_TYPES.UPDATE_MEALPLAN):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_MEALPLAN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MEALPLAN):
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

const apiUrl = 'api/meal-plans';

// Actions

export const getEntities: ICrudGetAllAction<IMealPlan> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MEALPLAN_LIST,
  payload: axios.get<IMealPlan>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IMealPlan> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MEALPLAN,
    payload: axios.get<IMealPlan>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMealPlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MEALPLAN,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMealPlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MEALPLAN,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IMealPlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_MEALPLAN,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMealPlan> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MEALPLAN,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
