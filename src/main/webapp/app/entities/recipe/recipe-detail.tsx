import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './recipe.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecipeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecipeDetail = (props: IRecipeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { recipeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recipeDetailsHeading">Recipe</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{recipeEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{recipeEntity.name}</dd>
          <dt>
            <span id="servings">Servings</span>
          </dt>
          <dd>{recipeEntity.servings}</dd>
          <dt>
            <span id="instructions">Instructions</span>
          </dt>
          <dd>{recipeEntity.instructions}</dd>
          <dt>
            <span id="additionalNotes">Additional Notes</span>
          </dt>
          <dd>{recipeEntity.additionalNotes}</dd>
          <dt>
            <span id="picture">Picture</span>
          </dt>
          <dd>
            {recipeEntity.picture ? (
              <div>
                {recipeEntity.pictureContentType ? (
                  <a onClick={openFile(recipeEntity.pictureContentType, recipeEntity.picture)}>
                    <img src={`data:${recipeEntity.pictureContentType};base64,${recipeEntity.picture}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {recipeEntity.pictureContentType}, {byteSize(recipeEntity.picture)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="source">Source</span>
          </dt>
          <dd>{recipeEntity.source}</dd>
          <dt>
            <span id="season">Season</span>
          </dt>
          <dd>{recipeEntity.season}</dd>
        </dl>
        <Button tag={Link} to="/recipe" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/recipe/${recipeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ recipe }: IRootState) => ({
  recipeEntity: recipe.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecipeDetail);
