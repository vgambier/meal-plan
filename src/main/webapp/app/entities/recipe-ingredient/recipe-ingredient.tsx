import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './recipe-ingredient.reducer';
import { IRecipeIngredient } from 'app/shared/model/recipe-ingredient.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecipeIngredientProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const RecipeIngredient = (props: IRecipeIngredientProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { recipeIngredientList, match, loading } = props;
  return (
    <div>
      <h2 id="recipe-ingredient-heading" data-cy="RecipeIngredientHeading">
        Recipe Ingredients
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Recipe Ingredient
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {recipeIngredientList && recipeIngredientList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Quantity</th>
                <th>Unit</th>
                <th>Optional</th>
                <th>Ingredient</th>
                <th>Recipe</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {recipeIngredientList.map((recipeIngredient, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${recipeIngredient.id}`} color="link" size="sm">
                      {recipeIngredient.id}
                    </Button>
                  </td>
                  <td>{recipeIngredient.quantity}</td>
                  <td>{recipeIngredient.unit}</td>
                  <td>{recipeIngredient.optional ? 'true' : 'false'}</td>
                  <td>
                    {recipeIngredient.ingredient ? (
                      <Link to={`ingredient/${recipeIngredient.ingredient.id}`}>{recipeIngredient.ingredient.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {recipeIngredient.recipe ? <Link to={`recipe/${recipeIngredient.recipe.id}`}>{recipeIngredient.recipe.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${recipeIngredient.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${recipeIngredient.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${recipeIngredient.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Recipe Ingredients found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ recipeIngredient }: IRootState) => ({
  recipeIngredientList: recipeIngredient.entities,
  loading: recipeIngredient.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecipeIngredient);
