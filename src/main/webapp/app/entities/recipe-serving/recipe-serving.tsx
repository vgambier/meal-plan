import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './recipe-serving.reducer';
import { IRecipeServing } from 'app/shared/model/recipe-serving.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecipeServingProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const RecipeServing = (props: IRecipeServingProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { recipeServingList, match, loading } = props;
  return (
    <div>
      <h2 id="recipe-serving-heading" data-cy="RecipeServingHeading">
        Recipe Servings
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Recipe Serving
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {recipeServingList && recipeServingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Servings Override</th>
                <th>Recipe</th>
                <th>Meal Plan</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {recipeServingList.map((recipeServing, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${recipeServing.id}`} color="link" size="sm">
                      {recipeServing.id}
                    </Button>
                  </td>
                  <td>{recipeServing.servingsOverride}</td>
                  <td>{recipeServing.recipe ? <Link to={`recipe/${recipeServing.recipe.id}`}>{recipeServing.recipe.id}</Link> : ''}</td>
                  <td>
                    {recipeServing.mealPlan ? <Link to={`meal-plan/${recipeServing.mealPlan.id}`}>{recipeServing.mealPlan.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${recipeServing.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${recipeServing.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${recipeServing.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Recipe Servings found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ recipeServing }: IRootState) => ({
  recipeServingList: recipeServing.entities,
  loading: recipeServing.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecipeServing);
