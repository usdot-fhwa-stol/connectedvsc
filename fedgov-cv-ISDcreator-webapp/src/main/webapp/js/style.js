const laneDefault = {
  // strokeColor: "${getStrokeColor}",
  strokeColor: "#FF9900",
  // fillColor: "${getFillColor}",
  fillColor: "#FF9900",
  strokeOpacity: 1,
  strokeWidth: 4,
  fillOpacity: .9,
  pointRadius: 6,
  label: "${getLabel}",
  fontFamily: "Arial",
  fontSize: "8px",
  cursor: "pointer"
};

const barDefault = {
  strokeColor: "#FF0000",
  fillColor: "#FF0000",
  strokeOpacity: 1,
  strokeWidth: 3,
  fillOpacity: 0,
  pointRadius: 2
};

const vectorDefault = {
  strokeColor: "#FF9900",
  fillColor: "#FF9900",
  strokeOpacity: 1,
  strokeWidth: 1,
  fillOpacity: 0,
  pointRadius: 1
};

const widthDefault = {
  strokeColor: "#FFFF00",
  fillColor: "#FFFF00",
  strokeOpacity: .5,
  strokeWidth: 1,
  fillOpacity: .1,
  pointRadius: 1
};

const connectionsDefault = {
  strokeColor: "#0000FF",
  fillColor: "#0000FF",
  strokeOpacity: 1,
  strokeWidth: 1,
  fillOpacity:.5,
  pointRadius: 6,
  graphicName: "triangle",
  rotation: "${angle}"
};


const laneStyle = (feature) => {
  return new ol.style.Style({
    stroke: new ol.style.Stroke({
      color: feature.get('computed') ? '#FF5000' : (feature.get('source') ? '#00EDFF' : "#FF9900"),
      width: laneDefault.strokeWidth,
      opacity: laneDefault.strokeOpacity
    }),
    fill: new ol.style.Fill({
      color: feature.get('computed') ? '#FF5000' : (feature.get('source') ? '#00EDFF' : "#FF9900"),
      opacity: laneDefault.fillOpacity
    }),
    text: new ol.style.Text({
      text: feature.get('laneNumber') ? feature.get('laneNumber').toString() : '',
      font: `${laneDefault.fontSize} ${laneDefault.fontFamily}`,
      fill: new ol.style.Fill({
        color: '#000'
      })
    }),
    image: new ol.style.Circle({
      radius: laneDefault.pointRadius,
      fill: new ol.style.Fill({
        color: feature.get('computed') ? '#FF5000' : (feature.get('source') ? '#00EDFF' : laneDefault.fillColor)
      })
    })
  });
};

const barStyle = (feature) => {
  return new ol.style.Style({
    stroke: new ol.style.Stroke({
      color: barDefault.strokeColor,
      width: barDefault.strokeWidth,
      opacity: barDefault.strokeOpacity
    })
  });
};


const vectorStyle = (feature)=>{
  console.log(feature);
  return new ol.style.Style(vectorDefault)
};

const widthStyle = (feature) => {
  return new ol.style.Style({
    stroke: new ol.style.Stroke({
      color: widthDefault.strokeColor,
      width: widthDefault.strokeWidth,
      opacity: widthDefault.strokeOpacity
    }),
    fill: new ol.style.Fill({
      color: widthDefault.fillColor,
      opacity: widthDefault.fillOpacity
    }),
    image: new ol.style.Circle({
      radius: widthDefault.pointRadius,
      fill: new ol.style.Fill({
        color: widthDefault.fillColor
      })
    })
  });
};

const connectionsStyle = (feature) => {
  return new ol.style.Style({
    stroke: new ol.style.Stroke({
      color: connectionsDefault.strokeColor,
      width: connectionsDefault.strokeWidth,
      opacity: connectionsDefault.strokeOpacity
    }),
    fill: new ol.style.Fill({
      color: connectionsDefault.fillColor,
      opacity: connectionsDefault.fillOpacity
    }),
    image: new ol.style.RegularShape({
      points: 3,
      radius: connectionsDefault.pointRadius,
      angle: feature.get('angle') || 0,
      fill: new ol.style.Fill({
        color: connectionsDefault.fillColor
      })
    })
  });
};


const lineStyle = new ol.style.Style({
  stroke: new ol.style.Stroke({
    color: 'red',
    width: 2
  })
});

const pointStyle = new ol.style.Style({
  image: new ol.style.Circle({
    radius: 5,
    fill: new ol.style.Fill({
      color: 'red'
    })
  })
});

const errorMarkerStyle = new ol.style.Style({
  image: new ol.style.Icon({
    src: 'img/error.png',
    scale: 0.1
  })
});

export {
  laneStyle,
  vectorStyle,
  lineStyle,
  widthStyle,
  connectionsStyle,
  errorMarkerStyle,
  barStyle,
  pointStyle
};