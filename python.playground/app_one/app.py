import streamlit as st
import pandas as pd
import numpy as np
import time

if __name__ == "__main__":
    # Add a select_box to the sidebar:
    add_select_box = st.sidebar.selectbox(
        'How would you like to be contacted?',
        ('Email', 'Home phone', 'Mobile phone')
    )

    'Starting a long computation...'

    # Add a placeholder
    latest_iteration = st.empty()
    bar = st.progress(0)

    for i in range(100):
        # Update the progress bar with each iteration.
        latest_iteration.text(f'Iteration {i + 1}')
        bar.progress(i + 1)
        time.sleep(0.1)

    x = st.slider('x')
    st.write(x, 'squared is', x * x)

    if st.checkbox('Show dataframe'):
        st.write("Here's our first attempt at using data to create a table:")
        df_1 = pd.DataFrame({
            'first column': [1, 2, 3, 4],
            'second column': [10, 20, 30, 40]
        })

        st.write(df_1)

    df_2 = pd.DataFrame(
        np.random.randn(10, 3),
        columns=('col %d' % i for i in range(3))
    ).style.highlight_max(axis=0)

    st.dataframe(df_2)
    st.line_chart(df_2)

    df_3 = pd.DataFrame(
        np.random.randn(1000, 2) / [50, 50] + [37.76, -122.4],
        columns=['lat', 'lon'])

    st.map(df_3)




